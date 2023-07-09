package com.example.movietvshowapp.model.search

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieAccept(
    val page: Int,
    @Embedded
    val results: List<SearchMovie>
)