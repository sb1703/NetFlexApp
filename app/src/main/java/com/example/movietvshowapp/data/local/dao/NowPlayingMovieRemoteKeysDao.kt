package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys

@Dao
interface NowPlayingMovieRemoteKeysDao {

    @Query("SELECT * FROM now_playing_movie_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): NowPlayingMovieRemoteKeys
//    suspend fun getRemoteKeys(id: String): NowPlayingMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<NowPlayingMovieRemoteKeys>)

    @Query("DELETE FROM now_playing_movie_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}