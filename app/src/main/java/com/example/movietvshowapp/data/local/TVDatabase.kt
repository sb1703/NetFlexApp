package com.example.movietvshowapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.local.dao.AiringTodayTVDao
import com.example.movietvshowapp.data.local.dao.AiringTodayTVRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.OnTheAirTVDao
import com.example.movietvshowapp.data.local.dao.OnTheAirTVRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.PopularTVDao
import com.example.movietvshowapp.data.local.dao.PopularTVRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.TopRatedTVDao
import com.example.movietvshowapp.data.local.dao.TopRatedTVRemoteKeysDao
import com.example.movietvshowapp.model.tv.AiringTodayTV
import com.example.movietvshowapp.model.tv.AiringTodayTVRemoteKeys
import com.example.movietvshowapp.model.tv.OnTheAirTV
import com.example.movietvshowapp.model.tv.OnTheAirTVRemoteKeys
import com.example.movietvshowapp.model.tv.PopularTV
import com.example.movietvshowapp.model.tv.PopularTVRemoteKeys
import com.example.movietvshowapp.model.tv.TopRatedTV
import com.example.movietvshowapp.model.tv.TopRatedTVRemoteKeys

@Database(
    entities = [AiringTodayTV::class, AiringTodayTVRemoteKeys::class, OnTheAirTV::class, OnTheAirTVRemoteKeys::class, PopularTV::class, PopularTVRemoteKeys::class, TopRatedTV::class, TopRatedTVRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class TVDatabase:RoomDatabase() {
    abstract fun airingTodayTVDao(): AiringTodayTVDao
    abstract fun airingTodayTVRemoteKeysDao(): AiringTodayTVRemoteKeysDao
    abstract fun onTheAirTVDao(): OnTheAirTVDao
    abstract fun onTheAirTVRemoteKeysDao(): OnTheAirTVRemoteKeysDao
    abstract fun popularTVDao(): PopularTVDao
    abstract fun popularTVRemoteKeysDao(): PopularTVRemoteKeysDao
    abstract fun topRatedTVDao(): TopRatedTVDao
    abstract fun topRatedTVRemoteKeysDao(): TopRatedTVRemoteKeysDao
}