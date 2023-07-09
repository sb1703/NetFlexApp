package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.search.SearchPerson

@Dao
interface SearchPersonDao {
    @Query("SELECT * FROM search_person_table")
    fun getAllImages(): PagingSource<Int, SearchPerson>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<SearchPerson>)

    @Query("DELETE FROM search_person_table")
    suspend fun deleteAllImages()
}