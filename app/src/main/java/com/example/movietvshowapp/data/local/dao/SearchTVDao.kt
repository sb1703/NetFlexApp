package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.search.SearchMovie
import com.example.movietvshowapp.model.search.SearchTV

@Dao
interface SearchTVDao {
    @Query("SELECT * FROM search_tv_table")
    fun getAllImages(): PagingSource<Int, SearchTV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<SearchTV>)

    @Query("DELETE FROM search_tv_table")
    suspend fun deleteAllImages()
}