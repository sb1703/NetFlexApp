package com.example.movietvshowapp.model.account

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetails(
    @Embedded
    @SerialName("avatar")
    val avatar: AccountAvatar,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val userName: String
)
