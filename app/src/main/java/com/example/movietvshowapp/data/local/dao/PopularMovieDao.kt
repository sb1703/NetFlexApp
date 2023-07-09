package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.PopularMovie

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movie_table")
    fun getAllImages(): PagingSource<Int, PopularMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<PopularMovie>)

    @Query("DELETE FROM popular_movie_table")
    suspend fun deleteAllImages()
}