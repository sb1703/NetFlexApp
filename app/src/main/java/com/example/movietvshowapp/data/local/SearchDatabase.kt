package com.example.movietvshowapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.local.dao.SearchMovieDao
import com.example.movietvshowapp.data.local.dao.SearchMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.SearchPersonDao
import com.example.movietvshowapp.data.local.dao.SearchPersonRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.SearchTVDao
import com.example.movietvshowapp.data.local.dao.SearchTVRemoteKeysDao
import com.example.movietvshowapp.model.search.SearchMovie
import com.example.movietvshowapp.model.search.SearchMovieRemoteKeys
import com.example.movietvshowapp.model.search.SearchPerson
import com.example.movietvshowapp.model.search.SearchPersonRemoteKeys
import com.example.movietvshowapp.model.search.SearchTV
import com.example.movietvshowapp.model.search.SearchTVRemoteKeys

@Database(
    entities = [SearchMovie::class,SearchMovieRemoteKeys::class,SearchTV::class,SearchTVRemoteKeys::class,SearchPerson::class,SearchPersonRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun searchMovieDao(): SearchMovieDao
    abstract fun searchMovieRemoteKeysDao(): SearchMovieRemoteKeysDao
    abstract fun searchTVDao(): SearchTVDao
    abstract fun searchTVRemoteKeysDao(): SearchTVRemoteKeysDao
    abstract fun searchPersonDao(): SearchPersonDao
    abstract fun searchPersonRemoteKeysDao(): SearchPersonRemoteKeysDao
}