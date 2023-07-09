package com.example.movietvshowapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movietvshowapp.data.local.DetailsDatabase
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.movieDetails.MovieReviewsRemoteKeys
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class MovieReviewsRemoteMediator(
    private val tmdrApi: TmdrApi,
    private val detailsDatabase: DetailsDatabase,
    private val movieId: String
):RemoteMediator<Int,MovieReviews>() {

    private val movieReviewsDao = detailsDatabase.movieReviewsDao()
    private val movieReviewsRemoteKeysDao = detailsDatabase.movieReviewsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieReviews>
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

            val response = tmdrApi.getAllMovieReviews(movieId = movieId, page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            detailsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieReviewsDao.deleteAllReviews()
                    movieReviewsRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { movieReviews ->
                    MovieReviewsRemoteKeys(
                        id = movieReviews.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                movieReviewsRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                movieReviewsDao.addReviews(reviews = response.results)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieReviews>
    ): MovieReviewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieReviewsRemoteKeysDao.getRemoteKeys(id = id)
                //                nowPlayingMovieRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieReviews>
    ): MovieReviewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movieReviews ->
                movieReviewsRemoteKeysDao.getRemoteKeys(id = movieReviews.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieReviews>
    ): MovieReviewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movieReviews ->
                movieReviewsRemoteKeysDao.getRemoteKeys(id = movieReviews.id)
            }
    }
}