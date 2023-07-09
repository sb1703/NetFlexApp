package com.example.movietvshowapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.AccountDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.account.FavouriteMovie
import com.example.movietvshowapp.model.account.FavouriteMovieRemoteKeys
import com.example.movietvshowapp.screens.account.AccountState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class FavouriteMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val accountDatabase: AccountDatabase,
    private val accountState: AccountState
):RemoteMediator<Int, FavouriteMovie>() {

    private val favouriteMovieDao = accountDatabase.favouriteMovieDao()
    private val favouriteMovieRemoteKeysDao = accountDatabase.favouriteMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavouriteMovie>
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

            val response = tmdrApi.getFavouriteMovies(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = currentPage)
            Log.d("session",accountState.accountDetails.id.toString())
            Log.d("session",accountState.session.sessionId)
            Log.d("session",response.results.toString())
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            accountDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    favouriteMovieDao.deleteAllImages()
                    favouriteMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { favouriteMovie ->
                    FavouriteMovieRemoteKeys(
                        id = favouriteMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                favouriteMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                favouriteMovieDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FavouriteMovie>
    ): FavouriteMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                favouriteMovieRemoteKeysDao.getRemoteKeys(id = id)
                //                favouriteMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FavouriteMovie>
    ): FavouriteMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                favouriteMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, FavouriteMovie>
    ): FavouriteMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                favouriteMovieRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}