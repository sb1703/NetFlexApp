package com.example.movietvshowapp.model.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movietvshowapp.util.Constants
import com.example.movietvshowapp.util.Constants.ON_THE_AIR_TV_REMOTE_KEYS_TABLE

@Entity(tableName = ON_THE_AIR_TV_REMOTE_KEYS_TABLE)
data class OnTheAirTVRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,                             // nullable type
    val nextPage: Int?                              // nullable type
)