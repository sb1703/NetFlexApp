package com.example.movietvshowapp.model.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants
import com.example.movietvshowapp.util.Constants.POPULAR_TV_REMOTE_KEYS_TABLE

@Entity(tableName = POPULAR_TV_REMOTE_KEYS_TABLE)
data class PopularTVRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)