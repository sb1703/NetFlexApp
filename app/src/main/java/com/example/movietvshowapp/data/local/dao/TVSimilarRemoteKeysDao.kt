package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieSimilarRemoteKeys
import com.example.movietvshowapp.model.tvDetails.TVSimilarRemoteKeys

@Dao
interface TVSimilarRemoteKeysDao {
    @Query("SELECT * FROM tv_similar_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): TVSimilarRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<TVSimilarRemoteKeys>)

    @Query("DELETE FROM tv_similar_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}