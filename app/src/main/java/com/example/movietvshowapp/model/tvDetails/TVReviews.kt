package com.example.movietvshowapp.model.tvDetails

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.model.movieDetails.AuthorDetails
import com.example.movietvshowapp.util.Constants
import com.example.movietvshowapp.util.Constants.TV_REVIEWS_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TV_REVIEWS_TABLE)
data class TVReviews(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerialName("author")
    val author: String,
    @Embedded
    @SerialName("author_details")
    val authorDetails: AuthorDetails,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val releaseDate: String
)
