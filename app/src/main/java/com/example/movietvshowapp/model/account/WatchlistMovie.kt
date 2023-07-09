package com.example.movietvshowapp.model.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_WATCHLIST_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(MOVIE_WATCHLIST_TABLE)
data class WatchlistMovie(
    val adult: Boolean,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String
)
