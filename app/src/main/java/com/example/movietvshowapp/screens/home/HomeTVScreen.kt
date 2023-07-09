package com.example.movietvshowapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.movietvshowapp.navigation.Screen
import com.example.movietvshowapp.screens.account.AccountState
import com.example.movietvshowapp.screens.common.ListContentAiringTodayTV
import com.example.movietvshowapp.screens.common.ListContentOnTheAirTV
import com.example.movietvshowapp.screens.common.ListContentPopularTV
import com.example.movietvshowapp.screens.common.ListContentTopRatedTV
import com.example.movietvshowapp.ui.theme.AppPrimaryColor

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeTVScreen(
    navController: NavHostController,
    onClickUpdateMovieId: (Int) -> Unit,
    accountState: AccountState,
    state: HomeState,
    onEvent: (HomeEvent)->Unit
){

    LaunchedEffect(key1 = Unit){
        onEvent(HomeEvent.UpdateGetAiringTodayTV)
        onEvent(HomeEvent.UpdateGetOnTheAirTV)
        onEvent(HomeEvent.UpdateGetPopularTV)
        onEvent(HomeEvent.UpdateGetTopRatedTV)
    }

    val getAllAiringTodayTVImages = state.getAiringTodayTV?.collectAsLazyPagingItems()
    val getAllOnTheAirTVImages = state.getOnTheAirTV?.collectAsLazyPagingItems()
    val getAllPopularTVImages = state.getPopularTV?.collectAsLazyPagingItems()
    val getAllTopRatedTVImages = state.getTopRatedTV?.collectAsLazyPagingItems()

    val color = if(isSystemInDarkTheme()){
        Color.Black} else {
        AppPrimaryColor
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                },
                onAccountClicked = {
                    if(accountState.isLoggedIn){
                        navController.navigate(Screen.Account.route){
                            popUpTo(Screen.Account.route)
                        }
                    } else{
                        navController.navigate(Screen.GuestAccount.route){
                            popUpTo(Screen.GuestAccount.route)
                        }
                    }
                },
                navController = navController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color)
                    .verticalScroll(rememberScrollState())
            ) {
                ListContentAiringTodayTV(items = getAllAiringTodayTVImages, navController = navController, onClickUpdateMovieId = {movieId: Int -> onClickUpdateMovieId(movieId)})
                ListContentOnTheAirTV(items = getAllOnTheAirTVImages, navController = navController, onClickUpdateMovieId = {movieId: Int -> onClickUpdateMovieId(movieId)})
                ListContentPopularTV(items = getAllPopularTVImages, navController = navController, onClickUpdateMovieId = {movieId: Int -> onClickUpdateMovieId(movieId)})
                ListContentTopRatedTV(items = getAllTopRatedTVImages, navController = navController, onClickUpdateMovieId = {movieId: Int -> onClickUpdateMovieId(movieId)})
            }
        }
    )

}