package com.example.movietvshowapp.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.SEARCH_TV_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = SEARCH_TV_TABLE)
data class SearchTV(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String
)