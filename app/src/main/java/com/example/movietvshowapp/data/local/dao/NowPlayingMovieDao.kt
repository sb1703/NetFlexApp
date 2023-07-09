package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie

@Dao
interface NowPlayingMovieDao {
    @Query("SELECT * FROM now_playing_movie_table")
    fun getAllImages(): PagingSource<Int, NowPlayingMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<NowPlayingMovie>)

    @Query("DELETE FROM now_playing_movie_table")
    suspend fun deleteAllImages()
}