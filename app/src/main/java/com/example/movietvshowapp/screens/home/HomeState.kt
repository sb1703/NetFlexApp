package com.example.movietvshowapp.screens.home

import androidx.paging.PagingData
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.PopularMovie
import com.example.movietvshowapp.model.movie.TopRatedMovie
import com.example.movietvshowapp.model.movie.UpcomingMovie
import com.example.movietvshowapp.model.tv.AiringTodayTV
import com.example.movietvshowapp.model.tv.OnTheAirTV
import com.example.movietvshowapp.model.tv.PopularTV
import com.example.movietvshowapp.model.tv.TopRatedTV
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val getNowPlayingMovie: Flow<PagingData<NowPlayingMovie>>? = null,
    val getPopularMovie: Flow<PagingData<PopularMovie>>? = null,
    val getTopRatedMovie: Flow<PagingData<TopRatedMovie>>? = null,
    val getUpcomingMovie: Flow<PagingData<UpcomingMovie>>? = null,
    val getAiringTodayTV: Flow<PagingData<AiringTodayTV>>? = null,
    val getOnTheAirTV: Flow<PagingData<OnTheAirTV>>? = null,
    val getPopularTV: Flow<PagingData<PopularTV>>? = null,
    val getTopRatedTV: Flow<PagingData<TopRatedTV>>? = null
)
