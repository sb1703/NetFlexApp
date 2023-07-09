package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.tv.AiringTodayTVRemoteKeys

@Dao
interface AiringTodayTVRemoteKeysDao {
    @Query("SELECT * FROM airing_today_tv_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): AiringTodayTVRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<AiringTodayTVRemoteKeys>)

    @Query("DELETE FROM airing_today_tv_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}