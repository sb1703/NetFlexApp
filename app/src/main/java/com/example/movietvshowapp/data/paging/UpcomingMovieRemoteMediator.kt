package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.movie.UpcomingMovie
import com.example.movietvshowapp.model.movie.UpcomingMovieRemoteKeys
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class UpcomingMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val movieDatabase: MovieDatabase
):RemoteMediator<Int,UpcomingMovie>() {
    private val upcomingMovieDao = movieDatabase.upcomingMovieDao()
    private val upcomingMovieRemoteKeysDao = movieDatabase.upcomingMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UpcomingMovie>
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

            val response = tmdrApi.getAllUpcomingMovie(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    upcomingMovieDao.deleteAllImages()
                    upcomingMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { upcomingMovie ->
                    UpcomingMovieRemoteKeys(
                        id = upcomingMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                upcomingMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                upcomingMovieDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UpcomingMovie>
    ): UpcomingMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                upcomingMovieRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UpcomingMovie>
    ): UpcomingMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                upcomingMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UpcomingMovie>
    ): UpcomingMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                upcomingMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}