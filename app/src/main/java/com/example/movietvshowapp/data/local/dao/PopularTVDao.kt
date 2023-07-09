package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.tv.PopularTV

@Dao
interface PopularTVDao {
    @Query("SELECT * FROM popular_tv_table")
    fun getAllImages(): PagingSource<Int, PopularTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<PopularTV>)

    @Query("DELETE FROM popular_tv_table")
    suspend fun deleteAllImages()
}