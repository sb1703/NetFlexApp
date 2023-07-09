package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieReviewsRemoteKeys
import com.example.movietvshowapp.model.tvDetails.TVReviewsRemoteKeys

@Dao
interface TVReviewsRemoteKeysDao {
    @Query("SELECT * FROM tv_reviews_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): TVReviewsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<TVReviewsRemoteKeys>)

    @Query("DELETE FROM tv_reviews_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}