package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.WatchlistTVRemoteKeys

@Dao
interface WatchlistTVRemoteKeysDao {
    @Query("SELECT * FROM tv_watchlist_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): WatchlistTVRemoteKeys
//    suspend fun getRemoteKeys(id: String): NowPlayingMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<WatchlistTVRemoteKeys>)

    @Query("DELETE FROM tv_watchlist_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}