package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.WatchlistMovie

@Dao
interface WatchlistMovieDao {
    @Query("SELECT * FROM movie_watchlist_table")
    fun getAllImages(): PagingSource<Int, WatchlistMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<WatchlistMovie>)

    @Query("DELETE FROM movie_watchlist_table")
    suspend fun deleteAllImages()
}