package com.example.movietvshowapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.movie.TopRatedMovieRemoteKeys

@Dao
interface TopRatedMovieRemoteKeysDao {
    @Query("SELECT * FROM top_rated_movie_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): TopRatedMovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<TopRatedMovieRemoteKeys>)

    @Query("DELETE FROM top_rated_movie_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}