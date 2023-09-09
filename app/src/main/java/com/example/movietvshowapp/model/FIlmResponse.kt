package com.example.movietvshowapp.model

import com.google.gson.annotations.SerializedName

data class FIlmResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Film>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
