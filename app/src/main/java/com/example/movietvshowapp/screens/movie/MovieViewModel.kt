package com.example.movietvshowapp.screens.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.movietvshowapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow(MovieState())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: MovieEvent){
        when(event){
            is MovieEvent.UpdateMovieId -> {
                _state.value.stack.addLast(event.movieId)
                _state.update { it.copy(
                    movieId = event.movieId
                ) }
            }

            MovieEvent.UpdateBackStackMovieId -> {
                _state.value.stack.removeLast()
                _state.update { it.copy(
                    movieId = _state.value.stack.last()
                ) }
            }

            MovieEvent.UpdateGetMovieDetails -> {
                _state.update { it.copy(
                    getMovieDetails = repository.getMovieDetails(state.value)
                ) }
            }

            MovieEvent.UpdateGetMovieSimilar -> {
                _state.update { it.copy(
                    getMovieSimilar = repository.getAllMovieSimilar(state.value)
                ) }
            }

            MovieEvent.UpdateGetTVDetails -> {
                _state.update { it.copy(
                    getTVDetails = repository.getTVDetails(state.value)
                ) }
            }
            MovieEvent.UpdateGetTVSimilar -> {
                _state.update { it.copy(
                    getTVSimilar = repository.getAllTVSimilar(state.value)
                ) }
            }

            MovieEvent.UpdateGetMovieReviews -> {
                _state.update { it.copy(
                    getMovieReviews = repository.getAllMovieReviews(state.value)
                ) }
            }
            MovieEvent.UpdateGetTVReviews -> {
                _state.update { it.copy(
                    getTVReviews = repository.getAllTVReviews(state.value)
                ) }
            }

            is MovieEvent.AddFavouritesMovie -> {
                if(!state.value.isAddedToFavourites){
                    repository.addFavourite(accountState = event.accountState, movieState = state.value, mediaType = "movie")
                    _state.update { it.copy(
                        isAddedToFavourites = true
                    ) }
                } else{
                    repository.deleteFavourite(accountState = event.accountState, movieState = state.value, mediaType = "movie")
                    _state.update { it.copy(
                        isAddedToFavourites = false
                    ) }
                }
            }
            is MovieEvent.AddFavouritesTV -> {
                if(!state.value.isAddedToFavourites){
                    repository.addFavourite(accountState = event.accountState, movieState = state.value, mediaType = "tv")
                    _state.update { it.copy(
                        isAddedToFavourites = true
                    ) }
                } else{
                    repository.deleteFavourite(accountState = event.accountState, movieState = state.value, mediaType = "tv")
                    _state.update { it.copy(
                        isAddedToFavourites = false
                    ) }
                }
            }
            is MovieEvent.AddWatchlistMovie -> {
                if(!state.value.isAddedToWatchlist){
                    repository.addWatchList(accountState = event.accountState, movieState = state.value, mediaType = "movie")
                    _state.update { it.copy(
                       isAddedToWatchlist  = true
                    ) }
                } else{
                    repository.deleteWatchList(accountState = event.accountState, movieState = state.value, mediaType = "movie")
                    _state.update { it.copy(
                        isAddedToWatchlist = false
                    ) }
                }
            }
            is MovieEvent.AddWatchlistTV -> {
                if(!state.value.isAddedToWatchlist){
                    repository.addWatchList(accountState = event.accountState, movieState = state.value, mediaType = "tv")
                    _state.update { it.copy(
                        isAddedToWatchlist  = true
                    ) }
                } else{
                    repository.deleteWatchList(accountState = event.accountState, movieState = state.value, mediaType = "tv")
                    _state.update { it.copy(
                        isAddedToWatchlist  = false
                    ) }
                }
            }

            is MovieEvent.UpdateIsAddFavouritesMovie -> {
                val flow = repository.getFavouriteMovieList(accountState = event.accountState)
                flow.collect{
                    if(it.id == state.value.movieId){
                        Log.d("session","Favourite = true viewModel")
                        _state.update { movieState ->
                            movieState.copy(
                                isAddedToFavourites = true
                            )
                        }
                    }
                }
            }

            is MovieEvent.UpdateIsAddFavouritesTV -> {
                val flow = repository.getFavouriteTVList(accountState = event.accountState)
                flow.collect{
                    if(it.id == state.value.movieId){
                        Log.d("session","Favourite = true viewModel")
                        _state.update { movieState ->
                            movieState.copy(
                                isAddedToFavourites = true
                            )
                        }
                    }
                }
            }
            is MovieEvent.UpdateIsAddWatchlistMovie -> {
                val flow = repository.getWatchlistMovieList(accountState = event.accountState)
                flow.collect{
                    if(it.id == state.value.movieId){
                        Log.d("session","Watchlist = true viewModel")
                        _state.update { movieState ->
                            movieState.copy(
                                isAddedToWatchlist = true
                            )
                        }
                    }
                }
            }
            is MovieEvent.UpdateIsAddWatchlistTV -> {
                val flow = repository.getWatchlistTVList(accountState = event.accountState)
                flow.collect{
                    if(it.id == state.value.movieId){
                        Log.d("session","Watchlist = true viewModel")
                        _state.update { movieState ->
                            movieState.copy(
                                isAddedToWatchlist = true
                            )
                        }
                    }
                }
            }

            MovieEvent.SetIsAddFavouriteFalse -> {
                _state.update { it.copy(
                    isAddedToFavourites = false
                ) }
            }
            MovieEvent.SetIsAddWatchlistFalse -> {
                _state.update { it.copy(
                    isAddedToWatchlist = false
                ) }
            }

        }
    }
}