package com.example.movietvshowapp.model.search

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
class SearchPersonAccept(
    val page: Int,
    @Embedded
    val results: List<SearchPerson>
)