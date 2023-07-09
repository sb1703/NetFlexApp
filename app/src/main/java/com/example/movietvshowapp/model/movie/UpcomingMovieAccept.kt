package com.example.movietvshowapp.model.movie

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingMovieAccept(
    val page: Int,
    @Embedded
    val results: List<UpcomingMovie>
)
