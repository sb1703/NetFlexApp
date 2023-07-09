package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.AccountDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.account.WatchlistTV
import com.example.movietvshowapp.model.account.WatchlistTVRemoteKeys
import com.example.movietvshowapp.screens.account.AccountState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class WatchlistTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val accountDatabase: AccountDatabase,
    private val accountState: AccountState
):RemoteMediator<Int, WatchlistTV>() {

    private val watchlistTVDao = accountDatabase.watchlistTVDao()
    private val watchlistTVRemoteKeysDao = accountDatabase.watchlistTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WatchlistTV>
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

            val response = tmdrApi.getWatchlistTV(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            accountDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    watchlistTVDao.deleteAllImages()
                    watchlistTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { watchlistTV ->
                    WatchlistTVRemoteKeys(
                        id = watchlistTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                watchlistTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                watchlistTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, WatchlistTV>
    ): WatchlistTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                watchlistTVRemoteKeysDao.getRemoteKeys(id = id)
                //                watchlistTVRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, WatchlistTV>
    ): WatchlistTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                watchlistTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, WatchlistTV>
    ): WatchlistTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                watchlistTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}