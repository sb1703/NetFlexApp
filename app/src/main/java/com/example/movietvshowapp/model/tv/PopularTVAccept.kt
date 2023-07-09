package com.example.movietvshowapp.model.tv

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class PopularTVAccept(
    val page: Int,
    @Embedded
    val results: List<PopularTV>
)
