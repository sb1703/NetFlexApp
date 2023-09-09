package com.example.movietvshowapp.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.movietvshowapp.screens.account.AccountEvent
import com.example.movietvshowapp.screens.account.AccountScreen
import com.example.movietvshowapp.screens.account.AccountState
import com.example.movietvshowapp.screens.account.GuestAccountScreen
import com.example.movietvshowapp.screens.account.LoginScreen
import com.example.movietvshowapp.screens.home.HomeEvent
import com.example.movietvshowapp.screens.home.HomeMovieScreen
import com.example.movietvshowapp.screens.home.HomeState
import com.example.movietvshowapp.screens.home.HomeTVScreen
import com.example.movietvshowapp.screens.movie.MovieDetailsScreen
import com.example.movietvshowapp.screens.movie.MovieEvent
import com.example.movietvshowapp.screens.movie.MovieReviewsScreen
import com.example.movietvshowapp.screens.movie.MovieState
import com.example.movietvshowapp.screens.movie.MovieViewModel
import com.example.movietvshowapp.screens.movie.TVDetailsScreen
import com.example.movietvshowapp.screens.movie.TVReviewsScreen
import com.example.movietvshowapp.screens.search.SearchEvent
import com.example.movietvshowapp.screens.search.SearchScreen
import com.example.movietvshowapp.screens.search.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onDataLoaded: () -> Unit,
    movieViewModel: MovieViewModel = hiltViewModel()
){
    val onMovieEvent = movieViewModel::onEvent

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        composable(
            route = Screen.HomeMovie.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            HomeMovieScreen(
                navController = navController,
                onClickUpdateMovieId = {movieId: Int ->
            Log.d("movieId",movieId.toString() + "updateMovieId")
            CoroutineScope(Dispatchers.IO).launch{
                    onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                    cancel()
                }
            })
        }
        composable(
            route = Screen.HomeTV.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            HomeTVScreen(
                navController = navController,
                onClickUpdateMovieId = { movieId: Int ->
                CoroutineScope(Dispatchers.IO).launch{
                    onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                    cancel()
                }
            })
        }
        composable(
            route = Screen.Search.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            SearchScreen(
                navController = navController,
                onClickUpdateMovieId = {movieId: Int ->
                CoroutineScope(Dispatchers.IO).launch{
                    onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                    cancel()
                }
            })
        }
        composable(
            route = Screen.Account.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {-300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {-300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            AccountScreen(
                navController = navController,
                onClickUpdateMovieId = { movieId: Int ->
                    CoroutineScope(Dispatchers.IO).launch{
                        onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                        cancel()
                    }
                }
            )
        }
        composable(
            route = Screen.MovieDetails.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            MovieDetailsScreen(
                navController = navController,
                onClickUpdateMovieId = { movieId: Int ->
                CoroutineScope(Dispatchers.IO).launch{
                    onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                    cancel()
                }
            })
        }
        composable(
            route = Screen.TVDetails.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            TVDetailsScreen(
                navController = navController,
                onClickUpdateMovieId = { movieId: Int ->
                CoroutineScope(Dispatchers.IO).launch{
                    onMovieEvent(MovieEvent.UpdateMovieId(movieId))
                    cancel()
                }
            })
        }
        composable(
            route = Screen.MovieReviews.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            MovieReviewsScreen(navController = navController)
        }
        composable(
            route = Screen.TVReviews.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            TVReviewsScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route,
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            LoginScreen(
                navController = navController,
                onDataLoaded = onDataLoaded
            )
        }
        composable(
            route = Screen.GuestAccount.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {-300},
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {-300},
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ){
            GuestAccountScreen(navController = navController)
        }
    }
}