package com.example.movietvshowapp.model.tvDetails

import androidx.room.Embedded
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import kotlinx.serialization.Serializable

@Serializable
data class TVSimilarAccept(
    val page: Int,
    @Embedded
    val results: List<TVSimilar>
)
