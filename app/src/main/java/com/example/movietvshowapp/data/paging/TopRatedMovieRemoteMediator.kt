package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.movie.TopRatedMovie
import com.example.movietvshowapp.model.movie.TopRatedMovieRemoteKeys
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class TopRatedMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val movieDatabase: MovieDatabase
):RemoteMediator<Int,TopRatedMovie>() {
    private val topRatedMovieDao = movieDatabase.topRatedMovieDao()
    private val topRatedMovieRemoteKeysDao = movieDatabase.topRatedMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopRatedMovie>
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

            val response = tmdrApi.getAllTopRatedMovie(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    topRatedMovieDao.deleteAllImages()
                    topRatedMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { topRatedMovie ->
                    TopRatedMovieRemoteKeys(
                        id = topRatedMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                topRatedMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                topRatedMovieDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopRatedMovie>
    ): TopRatedMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                topRatedMovieRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TopRatedMovie>
    ): TopRatedMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                topRatedMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TopRatedMovie>
    ): TopRatedMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                topRatedMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}