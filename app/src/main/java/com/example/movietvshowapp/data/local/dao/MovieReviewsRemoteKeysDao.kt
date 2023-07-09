package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieReviewsRemoteKeys

@Dao
interface MovieReviewsRemoteKeysDao {
    @Query("SELECT * FROM movie_reviews_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): MovieReviewsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieReviewsRemoteKeys>)

    @Query("DELETE FROM movie_reviews_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}