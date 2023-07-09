package com.example.movietvshowapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.local.dao.FavouriteMovieDao
import com.example.movietvshowapp.data.local.dao.FavouriteMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.FavouriteTVDao
import com.example.movietvshowapp.data.local.dao.FavouriteTVRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.WatchlistMovieDao
import com.example.movietvshowapp.data.local.dao.WatchlistMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.WatchlistTVDao
import com.example.movietvshowapp.data.local.dao.WatchlistTVRemoteKeysDao
import com.example.movietvshowapp.model.account.FavouriteMovie
import com.example.movietvshowapp.model.account.FavouriteMovieRemoteKeys
import com.example.movietvshowapp.model.account.FavouriteTV
import com.example.movietvshowapp.model.account.FavouriteTVRemoteKeys
import com.example.movietvshowapp.model.account.WatchlistMovie
import com.example.movietvshowapp.model.account.WatchlistMovieRemoteKeys
import com.example.movietvshowapp.model.account.WatchlistTV
import com.example.movietvshowapp.model.account.WatchlistTVRemoteKeys

@Database(
    entities = [FavouriteMovie::class, FavouriteMovieRemoteKeys::class, FavouriteTV::class, FavouriteTVRemoteKeys::class, WatchlistMovie::class, WatchlistMovieRemoteKeys::class, WatchlistTV::class, WatchlistTVRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AccountDatabase: RoomDatabase() {

    abstract fun favouriteMovieDao(): FavouriteMovieDao
    abstract fun favouriteMovieRemoteKeysDao(): FavouriteMovieRemoteKeysDao
    abstract fun favouriteTVDao(): FavouriteTVDao
    abstract fun favouriteTVRemoteKeysDao(): FavouriteTVRemoteKeysDao

    abstract fun watchlistMovieDao(): WatchlistMovieDao
    abstract fun watchlistMovieRemoteKeysDao(): WatchlistMovieRemoteKeysDao
    abstract fun watchlistTVDao(): WatchlistTVDao
    abstract fun watchlistTVRemoteKeysDao(): WatchlistTVRemoteKeysDao

}