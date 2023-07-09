package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.WatchlistMovieRemoteKeys

@Dao
interface WatchlistMovieRemoteKeysDao {
    @Query("SELECT * FROM movie_watchlist_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): WatchlistMovieRemoteKeys
//    suspend fun getRemoteKeys(id: String): NowPlayingMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<WatchlistMovieRemoteKeys>)

    @Query("DELETE FROM movie_watchlist_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}