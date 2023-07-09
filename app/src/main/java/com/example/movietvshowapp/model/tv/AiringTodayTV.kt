package com.example.movietvshowapp.model.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.AIRING_TODAY_TV_TABLE)
data class AiringTodayTV(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("poster_path")
    val posterPath: String?
)
