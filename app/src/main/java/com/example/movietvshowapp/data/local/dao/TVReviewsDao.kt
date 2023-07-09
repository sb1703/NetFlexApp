package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.tvDetails.TVReviews

@Dao
interface TVReviewsDao {
    @Query("SELECT * FROM tv_reviews_table")
    fun getAllReviews(): PagingSource<Int, TVReviews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<TVReviews>)

    @Query("DELETE FROM tv_reviews_table")
    suspend fun deleteAllReviews()
}