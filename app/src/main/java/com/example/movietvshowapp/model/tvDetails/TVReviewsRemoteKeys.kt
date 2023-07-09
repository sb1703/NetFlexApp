package com.example.movietvshowapp.model.tvDetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants
import com.example.movietvshowapp.util.Constants.TV_REVIEWS_REMOTE_KEYS_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TV_REVIEWS_REMOTE_KEYS_TABLE)
data class TVReviewsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)