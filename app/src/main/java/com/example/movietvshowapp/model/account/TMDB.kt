package com.example.movietvshowapp.model.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TMDB(
    @SerialName("avatar_path")
    val avatarPath: String?
)
