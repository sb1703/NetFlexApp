package com.example.movietvshowapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.SearchDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.search.SearchMovie
import com.example.movietvshowapp.model.search.SearchMovieRemoteKeys
import com.example.movietvshowapp.screens.search.SearchState
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class SearchMovieRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val searchDatabase: SearchDatabase,
    private val searchState: SearchState
): RemoteMediator<Int, SearchMovie>() {

    private val searchMovieDao = searchDatabase.searchMovieDao()
    private val searchMovieRemoteKeysDao = searchDatabase.searchMovieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchMovie>
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

            Log.d("searchType",searchState.searchTextState)
            val response = tmdrApi.getAllSearchMovie(page = currentPage, query = searchState.searchTextState)
            Log.d("searchType",response.results.toString() + "Response")
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            searchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchMovieDao.deleteAllImages()
                    searchMovieRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { searchMovie ->
                    SearchMovieRemoteKeys(
                        id = searchMovie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                searchMovieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                searchMovieDao.addImages(images = response.results)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchMovie>
    ): SearchMovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                searchMovieRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchMovie>
    ): SearchMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { searchMovie ->
                searchMovieRemoteKeysDao.getRemoteKeys(id = searchMovie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchMovie>
    ): SearchMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { searchMovie ->
                searchMovieRemoteKeysDao.getRemoteKeys(id = searchMovie.id)
            }
    }
}