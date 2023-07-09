package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import com.example.movietvshowapp.model.tvDetails.TVSimilar

@Dao
interface TVSimilarDao {
    @Query("SELECT * FROM tv_similar_table")
    fun getAllImages(): PagingSource<Int, TVSimilar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<TVSimilar>)

    @Query("DELETE FROM tv_similar_table")
    suspend fun deleteAllImages()
}