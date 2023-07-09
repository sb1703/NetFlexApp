package com.example.movietvshowapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.local.dao.NowPlayingMovieDao
import com.example.movietvshowapp.data.local.dao.NowPlayingMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.PopularMovieDao
import com.example.movietvshowapp.data.local.dao.PopularMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.TopRatedMovieDao
import com.example.movietvshowapp.data.local.dao.TopRatedMovieRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.UpcomingMovieDao
import com.example.movietvshowapp.data.local.dao.UpcomingMovieRemoteKeysDao
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.NowPlayingMovieRemoteKeys
import com.example.movietvshowapp.model.movie.PopularMovie
import com.example.movietvshowapp.model.movie.PopularMovieRemoteKeys
import com.example.movietvshowapp.model.movie.TopRatedMovie
import com.example.movietvshowapp.model.movie.TopRatedMovieRemoteKeys
import com.example.movietvshowapp.model.movie.UpcomingMovie
import com.example.movietvshowapp.model.movie.UpcomingMovieRemoteKeys

@Database(
    entities = [NowPlayingMovie::class,NowPlayingMovieRemoteKeys::class,PopularMovie::class,PopularMovieRemoteKeys::class,TopRatedMovie::class,TopRatedMovieRemoteKeys::class,UpcomingMovie::class,UpcomingMovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun nowPlayingMovieDao(): NowPlayingMovieDao
    abstract fun nowPlayingMovieRemoteKeysDao(): NowPlayingMovieRemoteKeysDao
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun popularMovieRemoteKeysDao(): PopularMovieRemoteKeysDao
    abstract fun topRatedMovieDao(): TopRatedMovieDao
    abstract fun topRatedMovieRemoteKeysDao(): TopRatedMovieRemoteKeysDao
    abstract fun upcomingMovieDao(): UpcomingMovieDao
    abstract fun upcomingMovieRemoteKeysDao(): UpcomingMovieRemoteKeysDao
}