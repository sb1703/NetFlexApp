package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.UpcomingMovie

@Dao
interface UpcomingMovieDao {
    @Query("SELECT * FROM upcoming_movie_table")
    fun getAllImages(): PagingSource<Int, UpcomingMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<UpcomingMovie>)

    @Query("DELETE FROM upcoming_movie_table")
    suspend fun deleteAllImages()
}