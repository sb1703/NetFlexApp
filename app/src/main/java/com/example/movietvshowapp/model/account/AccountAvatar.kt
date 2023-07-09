package com.example.movietvshowapp.model.account

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountAvatar(
    @Embedded
    @SerialName("tmdb")
    val tmdb: TMDB
)
