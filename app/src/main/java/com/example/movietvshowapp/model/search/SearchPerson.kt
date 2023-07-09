package com.example.movietvshowapp.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants.SEARCH_PERSON_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = SEARCH_PERSON_TABLE)
data class SearchPerson(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("known_for_department")
    val knownForDepartment: String
)