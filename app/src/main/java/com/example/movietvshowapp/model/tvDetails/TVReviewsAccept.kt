package com.example.movietvshowapp.model.tvDetails

import androidx.room.Embedded
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import kotlinx.serialization.Serializable

@Serializable
data class TVReviewsAccept(
    val page: Int,
    @Embedded
    val results: List<TVReviews>
)
