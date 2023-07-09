package com.example.movietvshowapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.SearchDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.search.SearchTV
import com.example.movietvshowapp.model.search.SearchTVRemoteKeys
import com.example.movietvshowapp.screens.search.SearchState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class SearchTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val searchDatabase: SearchDatabase,
    private val searchState: SearchState
): RemoteMediator<Int, SearchTV>() {

    private val searchTVDao = searchDatabase.searchTVDao()
    private val searchTVRemoteKeysDao = searchDatabase.searchTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchTV>
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

            Log.d("query-rm",searchState.searchTextState)
            val response = tmdrApi.getAllSearchTV(page = currentPage, query = searchState.searchTextState)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            searchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchTVDao.deleteAllImages()
                    searchTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { searchTV ->
                    SearchTVRemoteKeys(
                        id = searchTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                searchTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                searchTVDao.addImages(images = response.results)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchTV>
    ): SearchTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                searchTVRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchTV>
    ): SearchTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { searchTV ->
                searchTVRemoteKeysDao.getRemoteKeys(id = searchTV.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchTV>
    ): SearchTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { searchTV ->
                searchTVRemoteKeysDao.getRemoteKeys(id = searchTV.id)
            }
    }
}