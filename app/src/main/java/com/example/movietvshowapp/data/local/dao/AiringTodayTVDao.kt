package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.tv.AiringTodayTV

@Dao
interface AiringTodayTVDao {
    @Query("SELECT * FROM airing_today_tv_table")
    fun getAllImages(): PagingSource<Int, AiringTodayTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<AiringTodayTV>)

    @Query("DELETE FROM airing_today_tv_table")
    suspend fun deleteAllImages()
}