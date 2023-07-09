package com.example.movietvshowapp.model.account

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteTVAccept(
    val page: Int,
    @Embedded
    val results: List<FavouriteTV>
)