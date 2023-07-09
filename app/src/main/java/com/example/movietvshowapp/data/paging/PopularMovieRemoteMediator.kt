package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.movie.PopularMovie
import com.example.movietvshowapp.model.movie.PopularMovieRemoteKeys
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val movieDatabase: MovieDatabase
):RemoteMediator<Int,PopularMovie>() {
    private val popularMovieDao = movieDatabase.popularMovieDao()
    private val popularMovieRemoteKeysDao = movieDatabase.popularMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovie>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = (remoteKeys != null)
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = (remoteKeys != null)
                        )
                    nextPage
                }
            }

            val response = tmdrApi.getAllPopularMovie(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    popularMovieDao.deleteAllImages()
                    popularMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { popularMovie ->
                    PopularMovieRemoteKeys(
                        id = popularMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                popularMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                popularMovieDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                popularMovieRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                popularMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                popularMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}