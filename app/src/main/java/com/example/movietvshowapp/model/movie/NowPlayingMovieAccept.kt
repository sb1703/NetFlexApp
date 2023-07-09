package com.example.movietvshowapp.model.movie

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingMovieAccept(
    val page: Int,
    @Embedded
    val results: List<NowPlayingMovie>
)
