package com.example.movietvshowapp.data.remote

import com.example.movietvshowapp.BuildConfig
import com.example.movietvshowapp.model.account.AccountDetails
import com.example.movietvshowapp.model.account.AddFavourite
import com.example.movietvshowapp.model.account.AddWatchlist
import com.example.movietvshowapp.model.account.FavouriteMovieAccept
import com.example.movietvshowapp.model.account.FavouriteTVAccept
import com.example.movietvshowapp.model.account.WatchlistMovieAccept
import com.example.movietvshowapp.model.account.WatchlistTVAccept
import com.example.movietvshowapp.model.movie.NowPlayingMovieAccept
import com.example.movietvshowapp.model.movie.PopularMovieAccept
import com.example.movietvshowapp.model.movie.TopRatedMovieAccept
import com.example.movietvshowapp.model.movie.UpcomingMovieAccept
import com.example.movietvshowapp.model.movieDetails.MovieDetails
import com.example.movietvshowapp.model.movieDetails.MovieReviewsAccept
import com.example.movietvshowapp.model.movieDetails.MovieSimilarAccept
import com.example.movietvshowapp.model.search.SearchMovieAccept
import com.example.movietvshowapp.model.search.SearchPersonAccept
import com.example.movietvshowapp.model.search.SearchTVAccept
import com.example.movietvshowapp.model.session.GuestSession
import com.example.movietvshowapp.model.session.LoginRequestToken
import com.example.movietvshowapp.model.session.RequestToken
import com.example.movietvshowapp.model.session.Session
import com.example.movietvshowapp.model.tv.AiringTodayTVAccept
import com.example.movietvshowapp.model.tv.OnTheAirTVAccept
import com.example.movietvshowapp.model.tv.PopularTVAccept
import com.example.movietvshowapp.model.tv.TopRatedTVAccept
import com.example.movietvshowapp.model.tvDetails.TVDetails
import com.example.movietvshowapp.model.tvDetails.TVReviewsAccept
import com.example.movietvshowapp.model.tvDetails.TVSimilarAccept
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdrApi {

//    MOVIE LIST

    @GET("movie/now_playing?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllNowPlayingMovie(
        @Query("page") page: Int
    ): NowPlayingMovieAccept

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllPopularMovie(
        @Query("page") page: Int
    ): PopularMovieAccept

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllTopRatedMovie(
        @Query("page") page: Int
    ): TopRatedMovieAccept

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllUpcomingMovie(
        @Query("page") page: Int
    ): UpcomingMovieAccept

//    TV LIST

    @GET("tv/airing_today?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllAiringTodayTV(
        @Query("page") page: Int
    ): AiringTodayTVAccept

    @GET("tv/on_the_air?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllOnTheAirTV(
        @Query("page") page: Int
    ): OnTheAirTVAccept

    @GET("tv/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllPopularTV(
        @Query("page") page: Int
    ): PopularTVAccept

    @GET("tv/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllTopRatedTV(
        @Query("page") page: Int
    ): TopRatedTVAccept

//    SEARCH LIST

    @GET("search/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllSearchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMovieAccept

    @GET("search/tv?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllSearchTV(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchTVAccept

    @GET("search/person?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllSearchPerson(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchPersonAccept

//    MOVIE DETAILS

    @GET("movie/{movie_id}?api_key=${BuildConfig.API_KEY}&append_to_response=details")
    suspend fun getAllMovieDetails(
        @Path("movie_id") movieId: String                      // Path Param
    ): MovieDetails

    @GET("movie/{movie_id}/reviews?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllMovieReviews(
        @Path("movie_id") movieId: String,                      // Path Param
        @Query("page") page: Int
    ): MovieReviewsAccept

    @GET("movie/{movie_id}/similar?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllMovieSimilar(
        @Path("movie_id") movieId: String,                      // Path Param
        @Query("page") page: Int
    ): MovieSimilarAccept

//    TV DETAILS

    @GET("tv/{series_id}?api_key=${BuildConfig.API_KEY}&append_to_response=details")
    suspend fun getAllTVDetails(
        @Path("series_id") seriesId: String                      // Path Param
    ): TVDetails

    @GET("tv/{series_id}/reviews?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllTVReviews(
        @Path("series_id") seriesId: String,                      // Path Param
        @Query("page") page: Int
    ): TVReviewsAccept

    @GET("tv/{series_id}/similar?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllTVSimilar(
        @Path("series_id") seriesId: String,                      // Path Param
        @Query("page") page: Int
    ): TVSimilarAccept

//    Guest Session
    @GET("authentication/guest_session/new?api_key=${BuildConfig.API_KEY}")
    suspend fun getGuestSessionId(): GuestSession

//    Request Token
    @GET("authentication/token/new?api_key=${BuildConfig.API_KEY}")
    suspend fun getRequestToken(): RequestToken

//    Login Request Token
    @POST("authentication/token/validate_with_login?api_key=${BuildConfig.API_KEY}")
    suspend fun getLoginRequestToken(
        @Query("username") userName: String,
        @Query("password") password: String,
        @Query("request_token") requestToken: String
    ): LoginRequestToken

//    Session Id
    @POST("authentication/session/new?api_key=${BuildConfig.API_KEY}")
    suspend fun getSessionId(
        @Query("request_token") requestToken: String
    ): Session

//    Delete Session
    @DELETE("authentication/session?api_key=${BuildConfig.API_KEY}")
    suspend fun deleteSession(
        @Query("session_id") sessionId: String
    )

//    Account Details
    @GET("account?api_key=${BuildConfig.API_KEY}")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): AccountDetails

//    Add Favourite
//    { "media_type": "movie", "media_id": 1009722, "favorite": true }                  raw json
    @POST("account/{account_id}/favorite?api_key=${BuildConfig.API_KEY}")
    suspend fun addFavourite(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body RAW_BODY: AddFavourite
    )

//    Add to Watchlist
//    { "media_type": "movie", "media_id": 1009722, "watchlist": true }                 raw json
    @POST("account/{account_id}/watchlist?api_key=${BuildConfig.API_KEY}")
    suspend fun addWatchList(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body RAW_BODY: AddWatchlist
    )

//    Favourites
    @GET("account/{account_id}/favorite/movies?api_key=${BuildConfig.API_KEY}")
    suspend fun getFavouriteMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): FavouriteMovieAccept

    @GET("account/{account_id}/favorite/tv?api_key=${BuildConfig.API_KEY}")
    suspend fun getFavouriteTV(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): FavouriteTVAccept

//    Watchlist
    @GET("account/{account_id}/watchlist/movies?api_key=${BuildConfig.API_KEY}")
    suspend fun getWatchlistMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): WatchlistMovieAccept

    @GET("account/{account_id}/watchlist/tv?api_key=${BuildConfig.API_KEY}")
    suspend fun getWatchlistTV(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): WatchlistTVAccept
}