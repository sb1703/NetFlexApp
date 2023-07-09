package com.example.movietvshowapp.screens.home

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
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.UpdateGetAiringTodayTV -> {
                _state.update { it.copy(
                    getAiringTodayTV = repository.getAllAiringTodayTVImages()
                ) }
            }
            HomeEvent.UpdateGetNowPlayingMovie -> {
                _state.update { it.copy(
                    getNowPlayingMovie = repository.getAllNowPlayingMovieImages()
                ) }
            }
            HomeEvent.UpdateGetOnTheAirTV -> {
                _state.update { it.copy(
                    getOnTheAirTV = repository.getAllOnTheAirTVImages()
                ) }
            }
            HomeEvent.UpdateGetPopularMovie -> {
                _state.update { it.copy(
                    getPopularMovie = repository.getAllPopularMovieImages()
                ) }
            }
            HomeEvent.UpdateGetPopularTV -> {
                _state.update { it.copy(
                    getPopularTV = repository.getAllPopularTVImages()
                ) }
            }
            HomeEvent.UpdateGetTopRatedMovie -> {
                _state.update { it.copy(
                    getTopRatedMovie = repository.getAllTopRatedMovieImages()
                ) }
            }
            HomeEvent.UpdateGetTopRatedTV -> {
                _state.update { it.copy(
                    getTopRatedTV = repository.getAllTopRatedTVImages()
                ) }
            }
            HomeEvent.UpdateGetUpcomingMovie -> {
                _state.update { it.copy(
                    getUpcomingMovie = repository.getAllUpcomingMovieImages()
                ) }
            }
        }
    }

}