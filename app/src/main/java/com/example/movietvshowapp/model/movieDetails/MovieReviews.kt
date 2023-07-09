package com.example.movietvshowapp.model.movieDetails

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_REVIEWS_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = MOVIE_REVIEWS_TABLE)
data class MovieReviews(
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
