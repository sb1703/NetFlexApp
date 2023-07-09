package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.tv.AiringTodayTV
import com.example.movietvshowapp.model.tv.AiringTodayTVRemoteKeys
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AiringTodayTVRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val tvDatabase: TVDatabase
):RemoteMediator<Int, AiringTodayTV>() {
    private val airingTodayTVDao = tvDatabase.airingTodayTVDao()
    private val airingTodayTVRemoteKeysDao = tvDatabase.airingTodayTVRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AiringTodayTV>
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

            val response = tmdrApi.getAllAiringTodayTV(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            tvDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    airingTodayTVDao.deleteAllImages()
                    airingTodayTVRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { airingTodayTV ->
                    AiringTodayTVRemoteKeys(
                        id = airingTodayTV.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                airingTodayTVRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                airingTodayTVDao.addImages(images = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, AiringTodayTV>
    ): AiringTodayTVRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                airingTodayTVRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, AiringTodayTV>
    ): AiringTodayTVRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { airingTodayTV ->
                airingTodayTVRemoteKeysDao.getRemoteKeys(id = airingTodayTV.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, AiringTodayTV>
    ): AiringTodayTVRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { airingTodayTV ->
                airingTodayTVRemoteKeysDao.getRemoteKeys(id = airingTodayTV.id)
            }
    }
}