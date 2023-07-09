package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.WatchlistTV

@Dao
interface WatchlistTVDao {
    @Query("SELECT * FROM tv_watchlist_table")
    fun getAllImages(): PagingSource<Int, WatchlistTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<WatchlistTV>)

    @Query("DELETE FROM tv_watchlist_table")
    suspend fun deleteAllImages()
}