package com.example.movietvshowapp.model.tv

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class OnTheAirTVAccept(
    val page: Int,
    @Embedded
    val results: List<OnTheAirTV>
)
