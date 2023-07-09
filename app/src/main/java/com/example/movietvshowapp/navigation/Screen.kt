package com.example.movietvshowapp.navigation

sealed class Screen(
    val route: String,
    val title: String
){
    object HomeMovie: Screen(route = "home_movie_screen", title = "Movie")
    object HomeTV: Screen(route = "home_tv_screen", title = "TV")
    object Search: Screen(route = "search_screen", title = "Search")
    object Account: Screen(route = "account_screen", title = "Account")
    object GuestAccount: Screen(route = "guest_account_screen", title = "GuestAccount")
    object Login: Screen(route = "login_screen", title = "Login")
    object MovieDetails: Screen(route = "movie_details_screen", title = "MovieDetails")
    object TVDetails: Screen(route = "tv_details_screen", title = "TVDetails")
    object MovieReviews: Screen(route = "movie_reviews_screen", title = "MovieReviews")
    object TVReviews: Screen(route = "tv_reviews_screen", title = "TVReviews")
}