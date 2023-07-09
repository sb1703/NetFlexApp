package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.tv.TopRatedTV
import com.example.movietvshowapp.model.tv.TopRatedTVRemoteKeys
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class TopRatedTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val tvDatabase: TVDatabase
):RemoteMediator<Int, TopRatedTV>() {
    private val topRatedTVDao = tvDatabase.topRatedTVDao()
    private val topRatedTVRemoteKeysDao = tvDatabase.topRatedTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopRatedTV>
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

            val response = tmdrApi.getAllTopRatedTV(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            tvDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    topRatedTVDao.deleteAllImages()
                    topRatedTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { topRatedTV ->
                    TopRatedTVRemoteKeys(
                        id = topRatedTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                topRatedTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                topRatedTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopRatedTV>
    ): TopRatedTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                topRatedTVRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TopRatedTV>
    ): TopRatedTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                topRatedTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TopRatedTV>
    ): TopRatedTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                topRatedTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}