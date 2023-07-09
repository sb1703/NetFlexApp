package com.example.movietvshowapp.model.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.TV_FAVOURITES_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(TV_FAVOURITES_TABLE)
data class FavouriteTV(
    @SerialName("first_air_date")
    val firstAirDate: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String
)
