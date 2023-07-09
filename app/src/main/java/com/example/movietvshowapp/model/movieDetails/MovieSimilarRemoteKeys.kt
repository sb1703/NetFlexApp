package com.example.movietvshowapp.model.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_SIMILAR_REMOTE_KEYS_TABLE

@Entity(tableName = MOVIE_SIMILAR_REMOTE_KEYS_TABLE)
data class MovieSimilarRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)
