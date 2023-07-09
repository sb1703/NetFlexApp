package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.tv.OnTheAirTVRemoteKeys

@Dao
interface OnTheAirTVRemoteKeysDao {
    @Query("SELECT * FROM on_the_air_tv_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): OnTheAirTVRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<OnTheAirTVRemoteKeys>)

    @Query("DELETE FROM on_the_air_tv_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}