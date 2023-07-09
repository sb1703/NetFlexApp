package com.example.movietvshowapp.model.tvDetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants

@Entity(tableName = Constants.TV_SIMILAR_REMOTE_KEYS_TABLE)
data class TVSimilarRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)
