package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.tv.PopularTVRemoteKeys

@Dao
interface PopularTVRemoteKeysDao {
    @Query("SELECT * FROM popular_tv_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): PopularTVRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PopularTVRemoteKeys>)

    @Query("DELETE FROM popular_tv_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}