package com.example.movietvshowapp.model.tvDetails

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVDetails(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("overview")
    val overview: String
)
