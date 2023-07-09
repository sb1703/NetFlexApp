package com.example.movietvshowapp.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.SEARCH_MOVIE_REMOTE_KEYS_TABLE

@Entity(tableName = SEARCH_MOVIE_REMOTE_KEYS_TABLE)
data class SearchMovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)