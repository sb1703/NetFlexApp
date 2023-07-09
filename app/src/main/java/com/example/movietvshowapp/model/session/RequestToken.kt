package com.example.movietvshowapp.model.session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestToken(
    @SerialName("success")
    val success: Boolean,
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("expires_at")
    val expiresAt: String
)
