package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.tv.TopRatedTV

@Dao
interface TopRatedTVDao {
    @Query("SELECT * FROM top_rated_tv_table")
    fun getAllImages(): PagingSource<Int, TopRatedTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<TopRatedTV>)

    @Query("DELETE FROM top_rated_tv_table")
    suspend fun deleteAllImages()
}