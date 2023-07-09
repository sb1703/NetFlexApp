package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.account.FavouriteTVRemoteKeys

@Dao
interface FavouriteTVRemoteKeysDao {
    @Query("SELECT * FROM tv_favourites_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): FavouriteTVRemoteKeys
//    suspend fun getRemoteKeys(id: String): NowPlayingMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<FavouriteTVRemoteKeys>)

    @Query("DELETE FROM tv_favourites_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}