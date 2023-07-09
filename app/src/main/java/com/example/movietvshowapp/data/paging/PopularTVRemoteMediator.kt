package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.tv.PopularTV
import com.example.movietvshowapp.model.tv.PopularTVRemoteKeys
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val tvDatabase: TVDatabase
):RemoteMediator<Int, PopularTV>() {
    private val popularTVDao = tvDatabase.popularTVDao()
    private val popularTVRemoteKeysDao = tvDatabase.popularTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTV>
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

            val response = tmdrApi.getAllPopularTV(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            tvDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    popularTVDao.deleteAllImages()
                    popularTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { popularTV ->
                    PopularTVRemoteKeys(
                        id = popularTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                popularTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                popularTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PopularTV>
    ): PopularTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                popularTVRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PopularTV>
    ): PopularTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { nowPlayingMove ->
                popularTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PopularTV>
    ): PopularTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { nowPlayingMove ->
                popularTVRemoteKeysDao.getRemoteKeys(id = nowPlayingMove.id)
            }
    }
}