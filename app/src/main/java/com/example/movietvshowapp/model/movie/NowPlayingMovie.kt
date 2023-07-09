package com.example.movietvshowapp.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.NOW_PLAYING_MOVIE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = NOW_PLAYING_MOVIE_TABLE)
data class NowPlayingMovie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("poster_path")
    val posterPath: String?,
)