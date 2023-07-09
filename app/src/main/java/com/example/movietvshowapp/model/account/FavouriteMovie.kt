package com.example.movietvshowapp.model.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_FAVOURITES_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(MOVIE_FAVOURITES_TABLE)
data class FavouriteMovie(
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
