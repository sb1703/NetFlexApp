package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.search.SearchMovieRemoteKeys
import com.example.movietvshowapp.model.search.SearchTVRemoteKeys

@Dao
interface SearchTVRemoteKeysDao {
    @Query("SELECT * FROM search_tv_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): SearchTVRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<SearchTVRemoteKeys>)

    @Query("DELETE FROM search_tv_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}