package com.example.movietvshowapp.model.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_FAVOURITES_REMOTE_KEYS_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(MOVIE_FAVOURITES_REMOTE_KEYS_TABLE)
data class FavouriteMovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)
