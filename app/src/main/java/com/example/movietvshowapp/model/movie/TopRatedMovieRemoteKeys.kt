package com.example.movietvshowapp.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants
import com.example.movietvshowapp.util.Constants.TOP_RATED_MOVIE_REMOTE_KEYS_TABLE

@Entity(tableName = TOP_RATED_MOVIE_REMOTE_KEYS_TABLE)
data class TopRatedMovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)