package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieReviews

@Dao
interface MovieReviewsDao {
    @Query("SELECT * FROM movie_reviews_table")
    fun getAllReviews(): PagingSource<Int, MovieReviews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<MovieReviews>)

    @Query("DELETE FROM movie_reviews_table")
    suspend fun deleteAllReviews()
}