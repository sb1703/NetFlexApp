package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.FavouriteTV

@Dao
interface FavouriteTVDao {
    @Query("SELECT * FROM tv_favourites_table")
    fun getAllImages(): PagingSource<Int, FavouriteTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<FavouriteTV>)

    @Query("DELETE FROM tv_favourites_table")
    suspend fun deleteAllImages()
}