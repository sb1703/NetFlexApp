package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.tv.OnTheAirTV

@Dao
interface OnTheAirTVDao {
    @Query("SELECT * FROM on_the_air_tv_table")
    fun getAllImages(): PagingSource<Int, OnTheAirTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<OnTheAirTV>)

    @Query("DELETE FROM on_the_air_tv_table")
    suspend fun deleteAllImages()
}