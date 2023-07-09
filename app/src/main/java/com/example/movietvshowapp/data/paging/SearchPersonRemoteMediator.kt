package com.example.movietvshowapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.SearchDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.search.SearchPerson
import com.example.movietvshowapp.model.search.SearchPersonRemoteKeys
import com.example.movietvshowapp.screens.search.SearchState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class SearchPersonRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val searchDatabase: SearchDatabase,
    private val searchState: SearchState
): RemoteMediator<Int, SearchPerson>() {

    private val searchPersonDao = searchDatabase.searchPersonDao()
    private val searchPersonRemoteKeysDao = searchDatabase.searchPersonRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchPerson>
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
            val response = tmdrApi.getAllSearchPerson(page = currentPage, query = searchState.searchTextState)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            searchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchPersonDao.deleteAllImages()
                    searchPersonRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { searchPerson ->
                    SearchPersonRemoteKeys(
                        id = searchPerson.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                searchPersonRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                searchPersonDao.addImages(images = response.results)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchPerson>
    ): SearchPersonRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                searchPersonRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchPerson>
    ): SearchPersonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { searchPerson ->
                searchPersonRemoteKeysDao.getRemoteKeys(id = searchPerson.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchPerson>
    ): SearchPersonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { searchPerson ->
                searchPersonRemoteKeysDao.getRemoteKeys(id = searchPerson.id)
            }
    }
}