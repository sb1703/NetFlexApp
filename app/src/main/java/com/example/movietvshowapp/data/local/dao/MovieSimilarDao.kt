package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieSimilar

@Dao
interface MovieSimilarDao {
    @Query("SELECT * FROM movie_similar_table")
    fun getAllImages(): PagingSource<Int, MovieSimilar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<MovieSimilar>)

    @Query("DELETE FROM movie_similar_table")
    suspend fun deleteAllImages()
}