package com.example.movietvshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.movietvshowapp.navigation.SetupNavGraph
import com.example.movietvshowapp.screens.account.AccountViewModel
import com.example.movietvshowapp.screens.home.HomeViewModel
import com.example.movietvshowapp.screens.movie.MovieViewModel
import com.example.movietvshowapp.screens.search.SearchViewModel
import com.example.movietvshowapp.ui.theme.AppPrimaryColor
import com.example.movietvshowapp.ui.theme.MovieTVShowAppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            keepSplashOpened
        }
        setContent {
            MovieTVShowAppTheme {
                val color = if(isSystemInDarkTheme()){
                    Color.Black} else {
                    AppPrimaryColor
                }

                val navController = rememberNavController()
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                ) {
                    SetupNavGraph(
                        navController = navController,
                        onDataLoaded = {keepSplashOpened = false}
                    )
                }
            }
        }
    }
}