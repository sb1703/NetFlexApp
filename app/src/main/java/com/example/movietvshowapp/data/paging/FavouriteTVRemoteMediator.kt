package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.AccountDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.account.FavouriteTV
import com.example.movietvshowapp.model.account.FavouriteTVRemoteKeys
import com.example.movietvshowapp.screens.account.AccountState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class FavouriteTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val accountDatabase: AccountDatabase,
    private val accountState: AccountState
):RemoteMediator<Int, FavouriteTV>() {

    private val favouriteTVDao = accountDatabase.favouriteTVDao()
    private val favouriteTVRemoteKeysDao = accountDatabase.favouriteTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavouriteTV>
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

            val response = tmdrApi.getFavouriteTV(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            accountDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    favouriteTVDao.deleteAllImages()
                    favouriteTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { favouriteTV ->
                    FavouriteTVRemoteKeys(
                        id = favouriteTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                favouriteTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                favouriteTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FavouriteTV>
    ): FavouriteTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                favouriteTVRemoteKeysDao.getRemoteKeys(id = id)
                //                favouriteTVRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FavouriteTV>
    ): FavouriteTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                favouriteTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, FavouriteTV>
    ): FavouriteTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                favouriteTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}