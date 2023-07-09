package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.TopRatedMovie

@Dao
interface TopRatedMovieDao {
    @Query("SELECT * FROM top_rated_movie_table")
    fun getAllImages(): PagingSource<Int, TopRatedMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<TopRatedMovie>)

    @Query("DELETE FROM top_rated_movie_table")
    suspend fun deleteAllImages()
}