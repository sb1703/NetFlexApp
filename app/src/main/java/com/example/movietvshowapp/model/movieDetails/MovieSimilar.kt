package com.example.movietvshowapp.model.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.MOVIE_SIMILAR_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = MOVIE_SIMILAR_TABLE)
data class MovieSimilar(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("poster_path")
    val posterPath: String?,
)
