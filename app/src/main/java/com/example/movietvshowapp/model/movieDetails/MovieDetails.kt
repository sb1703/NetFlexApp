package com.example.movietvshowapp.model.movieDetails

import androidx.room.Embedded
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("overview")
    val overview: String
)
