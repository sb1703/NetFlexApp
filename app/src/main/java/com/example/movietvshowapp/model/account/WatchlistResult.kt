package com.example.movietvshowapp.model.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistResult(
    @SerialName("success")
    val success: Boolean,
    @SerialName("status_message")
    val statusMessage: String
)
