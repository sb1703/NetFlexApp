package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.movie.PopularMovieRemoteKeys

@Dao
interface PopularMovieRemoteKeysDao {

    @Query("SELECT * FROM popular_movie_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): PopularMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PopularMovieRemoteKeys>)

    @Query("DELETE FROM popular_movie_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}