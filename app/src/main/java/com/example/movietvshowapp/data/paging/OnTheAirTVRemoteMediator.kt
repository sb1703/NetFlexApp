package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.tv.OnTheAirTV
import com.example.movietvshowapp.model.tv.OnTheAirTVRemoteKeys
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class OnTheAirTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val tvDatabase: TVDatabase
):RemoteMediator<Int, OnTheAirTV>() {
    private val onTheAirTVDao = tvDatabase.onTheAirTVDao()
    private val onTheAirTVRemoteKeysDao = tvDatabase.onTheAirTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OnTheAirTV>
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

            val response = tmdrApi.getAllOnTheAirTV(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            tvDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    onTheAirTVDao.deleteAllImages()
                    onTheAirTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { onTheAirTV ->
                    OnTheAirTVRemoteKeys(
                        id = onTheAirTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                onTheAirTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                onTheAirTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, OnTheAirTV>
    ): OnTheAirTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                onTheAirTVRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, OnTheAirTV>
    ): OnTheAirTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                onTheAirTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, OnTheAirTV>
    ): OnTheAirTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                onTheAirTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}