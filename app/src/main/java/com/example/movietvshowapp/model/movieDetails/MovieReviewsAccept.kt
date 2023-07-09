package com.example.movietvshowapp.model.movieDetails

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewsAccept(
    val page: Int,
    @Embedded
    val results: List<MovieReviews>
)