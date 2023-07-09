package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.DetailsDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.tvDetails.TVReviews
import com.example.movietvshowapp.model.tvDetails.TVReviewsRemoteKeys
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class TVReviewsRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val detailsDatabase: DetailsDatabase,
    private val seriesId: String
):RemoteMediator<Int, TVReviews>() {

    private val tVReviewsDao = detailsDatabase.tvReviewsDao()
    private val tVReviewsRemoteKeysDao = detailsDatabase.tvReviewsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TVReviews>
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

            val response = tmdrApi.getAllTVReviews(seriesId = seriesId, page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            detailsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tVReviewsDao.deleteAllReviews()
                    tVReviewsRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { tVReviews ->
                    TVReviewsRemoteKeys(
                        id = tVReviews.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                tVReviewsRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                tVReviewsDao.addReviews(reviews = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TVReviews>
    ): TVReviewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tVReviewsRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TVReviews>
    ): TVReviewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tVReviews ->
                tVReviewsRemoteKeysDao.getRemoteKeys(id = tVReviews.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TVReviews>
    ): TVReviewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tVReviews ->
                tVReviewsRemoteKeysDao.getRemoteKeys(id = tVReviews.id)
            }
    }
}