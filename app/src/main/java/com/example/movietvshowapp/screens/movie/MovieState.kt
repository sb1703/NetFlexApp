package com.example.movietvshowapp.screens.movie

import androidx.paging.PagingData
import com.example.movietvshowapp.model.movieDetails.MovieDetails
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import com.example.movietvshowapp.model.tvDetails.TVDetails
import com.example.movietvshowapp.model.tvDetails.TVReviews
import com.example.movietvshowapp.model.tvDetails.TVSimilar
import kotlinx.coroutines.flow.Flow

data class MovieState(
    val stack: ArrayDeque<Int?> = ArrayDeque(listOf<Int?>(null)),
    val movieId: Int? = null,
    val getMovieDetails: MovieDetails? = null,
    val getMovieSimilar: Flow<PagingData<MovieSimilar>>? = null,
    val getTVDetails: TVDetails? = null,
    val getTVSimilar: Flow<PagingData<TVSimilar>>? = null,
    val getMovieReviews: Flow<PagingData<MovieReviews>>? = null,
    val getTVReviews: Flow<PagingData<TVReviews>>? = null,
    val isAddedToFavourites: Boolean = false,
    val isAddedToWatchlist: Boolean = false
)
