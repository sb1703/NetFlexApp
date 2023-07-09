package com.example.movietvshowapp.screens.movie

import com.example.movietvshowapp.screens.account.AccountState


sealed interface MovieEvent{
    data class UpdateMovieId(val movieId: Int?): MovieEvent
    object UpdateBackStackMovieId: MovieEvent
    object UpdateGetMovieDetails: MovieEvent
    object UpdateGetMovieSimilar: MovieEvent
    object UpdateGetTVDetails: MovieEvent
    object UpdateGetTVSimilar: MovieEvent
    object UpdateGetMovieReviews: MovieEvent
    object UpdateGetTVReviews: MovieEvent
    data class AddFavouritesMovie(val accountState: AccountState): MovieEvent
    data class AddFavouritesTV(val accountState: AccountState): MovieEvent
    data class AddWatchlistMovie(val accountState: AccountState): MovieEvent
    data class AddWatchlistTV(val accountState: AccountState): MovieEvent
    data class UpdateIsAddFavouritesMovie(val accountState: AccountState): MovieEvent
    data class UpdateIsAddWatchlistMovie(val accountState: AccountState): MovieEvent
    data class UpdateIsAddFavouritesTV(val accountState: AccountState): MovieEvent
    data class UpdateIsAddWatchlistTV(val accountState: AccountState): MovieEvent
    object SetIsAddFavouriteFalse: MovieEvent
    object SetIsAddWatchlistFalse: MovieEvent
}