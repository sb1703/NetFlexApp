package com.example.movietvshowapp.model.search

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class SearchTVAccept(
    val page: Int,
    @Embedded
    val results: List<SearchTV>
)