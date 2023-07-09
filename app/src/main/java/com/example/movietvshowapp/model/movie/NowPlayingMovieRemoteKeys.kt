package com.example.movietvshowapp.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.NOW_PLAYING_MOVIE_REMOTE_KEYS_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = NOW_PLAYING_MOVIE_REMOTE_KEYS_TABLE)
data class NowPlayingMovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)