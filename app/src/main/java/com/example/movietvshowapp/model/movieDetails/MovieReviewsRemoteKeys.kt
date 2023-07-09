package com.example.movietvshowapp.model.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_REVIEWS_REMOTE_KEYS_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = MOVIE_REVIEWS_REMOTE_KEYS_TABLE)
data class MovieReviewsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)
