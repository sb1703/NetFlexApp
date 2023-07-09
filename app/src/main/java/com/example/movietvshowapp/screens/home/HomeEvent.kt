package com.example.movietvshowapp.screens.home


sealed interface HomeEvent{
    object UpdateGetNowPlayingMovie: HomeEvent
    object UpdateGetPopularMovie: HomeEvent
    object UpdateGetTopRatedMovie: HomeEvent
    object UpdateGetUpcomingMovie: HomeEvent
    object UpdateGetAiringTodayTV: HomeEvent
    object UpdateGetOnTheAirTV: HomeEvent
    object UpdateGetPopularTV: HomeEvent
    object UpdateGetTopRatedTV: HomeEvent
}