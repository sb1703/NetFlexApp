package com.example.movietvshowapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.local.dao.MovieReviewsDao
import com.example.movietvshowapp.data.local.dao.MovieReviewsRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.MovieSimilarDao
import com.example.movietvshowapp.data.local.dao.MovieSimilarRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.TVReviewsDao
import com.example.movietvshowapp.data.local.dao.TVReviewsRemoteKeysDao
import com.example.movietvshowapp.data.local.dao.TVSimilarDao
import com.example.movietvshowapp.data.local.dao.TVSimilarRemoteKeysDao
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.movieDetails.MovieReviewsRemoteKeys
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import com.example.movietvshowapp.model.movieDetails.MovieSimilarRemoteKeys
import com.example.movietvshowapp.model.tvDetails.TVReviews
import com.example.movietvshowapp.model.tvDetails.TVReviewsRemoteKeys
import com.example.movietvshowapp.model.tvDetails.TVSimilar
import com.example.movietvshowapp.model.tvDetails.TVSimilarRemoteKeys

@Database(
    entities = [MovieReviews::class,MovieReviewsRemoteKeys::class,MovieSimilar::class,MovieSimilarRemoteKeys::class, TVReviews::class, TVReviewsRemoteKeys::class, TVSimilar::class, TVSimilarRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class DetailsDatabase: RoomDatabase() {

    abstract fun movieReviewsDao(): MovieReviewsDao
    abstract fun movieReviewsRemoteKeysDao(): MovieReviewsRemoteKeysDao
    abstract fun movieSimilarDao(): MovieSimilarDao
    abstract fun movieSimilarRemoteKeysDao(): MovieSimilarRemoteKeysDao

    abstract fun tvReviewsDao(): TVReviewsDao
    abstract fun tvReviewsRemoteKeysDao(): TVReviewsRemoteKeysDao
    abstract fun tvSimilarDao(): TVSimilarDao
    abstract fun tvSimilarRemoteKeysDao(): TVSimilarRemoteKeysDao

}