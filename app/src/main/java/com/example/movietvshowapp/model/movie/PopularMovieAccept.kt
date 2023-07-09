package com.example.movietvshowapp.model.movie

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class PopularMovieAccept(
    val page: Int,
    @Embedded
    val results: List<PopularMovie>
)
