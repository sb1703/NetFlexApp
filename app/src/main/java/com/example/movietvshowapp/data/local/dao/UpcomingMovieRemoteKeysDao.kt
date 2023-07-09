package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.movie.UpcomingMovieRemoteKeys

@Dao
interface UpcomingMovieRemoteKeysDao {
    @Query("SELECT * FROM upcoming_movie_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): UpcomingMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<UpcomingMovieRemoteKeys>)

    @Query("DELETE FROM upcoming_movie_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}