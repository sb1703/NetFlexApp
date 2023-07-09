package com.example.movietvshowapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NowPlayingMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val movieDatabase: MovieDatabase
):RemoteMediator<Int,NowPlayingMovie>() {

    private val nowPlayingMovieDao = movieDatabase.nowPlayingMovieDao()
    private val nowPlayingMovieRemoteKeysDao = movieDatabase.nowPlayingMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NowPlayingMovie>
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

            val response = tmdrApi.getAllNowPlayingMovie(page = currentPage)
            Log.d("session",response.toString() + " Remote Mediator")
            Log.d("session",response.results.toString() + " Remote Mediator")
            Log.d("session",response.page.toString() + " Remote Mediator")
            val endOfPaginationReached = response.results.isEmpty()
            Log.d("session",endOfPaginationReached.toString() + " Remote Mediator")

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nowPlayingMovieDao.deleteAllImages()
                    nowPlayingMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { nowPlayingMovie ->
                    NowPlayingMovieRemoteKeys(
                        id = nowPlayingMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                nowPlayingMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                nowPlayingMovieDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            Log.d("session","Error " + e.toString() + " Remote Mediator")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, NowPlayingMovie>
    ): NowPlayingMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id)
        //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, NowPlayingMovie>
    ): NowPlayingMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, NowPlayingMovie>
    ): NowPlayingMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}