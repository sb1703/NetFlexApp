package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movieDetails.MovieReviewsRemoteKeys
import com.example.movietvshowapp.model.movieDetails.MovieSimilarRemoteKeys

@Dao
interface MovieSimilarRemoteKeysDao {
    @Query("SELECT * FROM movie_similar_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MovieSimilarRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieSimilarRemoteKeys>)

    @Query("DELETE FROM movie_similar_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}