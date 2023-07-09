package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.FavouriteMovie

@Dao
interface FavouriteMovieDao {
    @Query("SELECT * FROM movie_favourites_table")
    fun getAllImages(): PagingSource<Int, FavouriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<FavouriteMovie>)

    @Query("DELETE FROM movie_favourites_table")
    suspend fun deleteAllImages()
}