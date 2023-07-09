package com.example.movietvshowapp.model.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddFavourite(
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("media_id")
    val mediaId: Int?,
    @SerialName("favorite")
    val favorite: Boolean
)
