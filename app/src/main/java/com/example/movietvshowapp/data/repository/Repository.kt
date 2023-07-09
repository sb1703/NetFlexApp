package com.example.movietvshowapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movietvshowapp.data.local.AccountDatabase
import com.example.movietvshowapp.data.local.DetailsDatabase
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.local.SearchDatabase
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.data.paging.AiringTodayTVRemoteMediator
import com.example.movietvshowapp.data.paging.FavouriteMovieRemoteMediator
import com.example.movietvshowapp.data.paging.FavouriteTVRemoteMediator
import com.example.movietvshowapp.data.paging.MovieReviewsRemoteMediator
import com.example.movietvshowapp.data.paging.MovieSimilarRemoteMediator
import com.example.movietvshowapp.data.paging.NowPlayingMovieRemoteMediator
import com.example.movietvshowapp.data.paging.OnTheAirTVRemoteMediator
import com.example.movietvshowapp.data.paging.PopularMovieRemoteMediator
import com.example.movietvshowapp.data.paging.PopularTVRemoteMediator
import com.example.movietvshowapp.data.paging.SearchMovieRemoteMediator
import com.example.movietvshowapp.data.paging.SearchPersonRemoteMediator
import com.example.movietvshowapp.data.paging.SearchTVRemoteMediator
import com.example.movietvshowapp.data.paging.TVReviewsRemoteMediator
import com.example.movietvshowapp.data.paging.TVSimilarRemoteMediator
import com.example.movietvshowapp.data.paging.TopRatedMovieRemoteMediator
import com.example.movietvshowapp.data.paging.TopRatedTVRemoteMediator
import com.example.movietvshowapp.data.paging.UpcomingMovieRemoteMediator
import com.example.movietvshowapp.data.paging.WatchlistMovieRemoteMediator
import com.example.movietvshowapp.data.paging.WatchlistTVRemoteMediator
import com.example.movietvshowapp.data.remote.TmdrApi
import com.example.movietvshowapp.model.account.AccountDetails
import com.example.movietvshowapp.model.account.AddFavourite
import com.example.movietvshowapp.model.account.AddWatchlist
import com.example.movietvshowapp.model.account.FavouriteMovie
import com.example.movietvshowapp.model.account.FavouriteTV
import com.example.movietvshowapp.model.account.WatchlistMovie
import com.example.movietvshowapp.model.account.WatchlistTV
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.movie.PopularMovie
import com.example.movietvshowapp.model.movie.TopRatedMovie
import com.example.movietvshowapp.model.movie.UpcomingMovie
import com.example.movietvshowapp.model.movieDetails.MovieDetails
import com.example.movietvshowapp.model.movieDetails.MovieReviews
import com.example.movietvshowapp.model.movieDetails.MovieSimilar
import com.example.movietvshowapp.model.search.SearchMovie
import com.example.movietvshowapp.model.search.SearchPerson
import com.example.movietvshowapp.model.search.SearchTV
import com.example.movietvshowapp.model.session.GuestSession
import com.example.movietvshowapp.model.session.LoginRequestToken
import com.example.movietvshowapp.model.session.RequestToken
import com.example.movietvshowapp.model.session.Session
import com.example.movietvshowapp.model.tv.AiringTodayTV
import com.example.movietvshowapp.model.tv.OnTheAirTV
import com.example.movietvshowapp.model.tv.PopularTV
import com.example.movietvshowapp.model.tv.TopRatedTV
import com.example.movietvshowapp.model.tvDetails.TVDetails
import com.example.movietvshowapp.model.tvDetails.TVReviews
import com.example.movietvshowapp.model.tvDetails.TVSimilar
import com.example.movietvshowapp.screens.account.AccountState
import com.example.movietvshowapp.screens.movie.MovieState
import com.example.movietvshowapp.screens.search.SearchState
import com.example.movietvshowapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.pow

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val tmdrApi: TmdrApi,
    private val movieDatabase: MovieDatabase,
    private val tvDatabase: TVDatabase,
    private val searchDatabase: SearchDatabase,
    private val detailsDatabase: DetailsDatabase,
    private val accountDatabase: AccountDatabase
) {

    fun getAllNowPlayingMovieImages(): Flow<PagingData<NowPlayingMovie>> {
        val pagingSourceFactory = { movieDatabase.nowPlayingMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = NowPlayingMovieRemoteMediator(
                tmdrApi = tmdrApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllPopularMovieImages(): Flow<PagingData<PopularMovie>> {
        val pagingSourceFactory = { movieDatabase.popularMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularMovieRemoteMediator(
                tmdrApi = tmdrApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllTopRatedMovieImages(): Flow<PagingData<TopRatedMovie>> {
        val pagingSourceFactory = { movieDatabase.topRatedMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TopRatedMovieRemoteMediator(
                tmdrApi = tmdrApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllUpcomingMovieImages(): Flow<PagingData<UpcomingMovie>> {
        val pagingSourceFactory = { movieDatabase.upcomingMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UpcomingMovieRemoteMediator(
                tmdrApi = tmdrApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllAiringTodayTVImages(): Flow<PagingData<AiringTodayTV>> {
        val pagingSourceFactory = { tvDatabase.airingTodayTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = AiringTodayTVRemoteMediator(
                tmdrApi = tmdrApi,
                tvDatabase = tvDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllOnTheAirTVImages(): Flow<PagingData<OnTheAirTV>> {
        val pagingSourceFactory = { tvDatabase.onTheAirTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = OnTheAirTVRemoteMediator(
                tmdrApi = tmdrApi,
                tvDatabase = tvDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllPopularTVImages(): Flow<PagingData<PopularTV>> {
        val pagingSourceFactory = { tvDatabase.popularTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularTVRemoteMediator(
                tmdrApi = tmdrApi,
                tvDatabase = tvDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllTopRatedTVImages(): Flow<PagingData<TopRatedTV>> {
        val pagingSourceFactory = { tvDatabase.topRatedTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TopRatedTVRemoteMediator(
                tmdrApi = tmdrApi,
                tvDatabase = tvDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllSearchMovieImages(searchState: SearchState): Flow<PagingData<SearchMovie>> {
        val pagingSourceFactory = { searchDatabase.searchMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = SearchMovieRemoteMediator(
                tmdrApi = tmdrApi,
                searchDatabase = searchDatabase,
                searchState = searchState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllSearchTVImages(searchState: SearchState): Flow<PagingData<SearchTV>> {
        val pagingSourceFactory = { searchDatabase.searchTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = SearchTVRemoteMediator(
                tmdrApi = tmdrApi,
                searchDatabase = searchDatabase,
                searchState = searchState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllSearchPersonImages(searchState: SearchState): Flow<PagingData<SearchPerson>> {
        val pagingSourceFactory = { searchDatabase.searchPersonDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = SearchPersonRemoteMediator(
                tmdrApi = tmdrApi,
                searchDatabase = searchDatabase,
                searchState = searchState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllMovieReviews(movieState: MovieState): Flow<PagingData<MovieReviews>> {
        val pagingSourceFactory = { detailsDatabase.movieReviewsDao().getAllReviews() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = MovieReviewsRemoteMediator(
                tmdrApi = tmdrApi,
                detailsDatabase = detailsDatabase,
                movieId = movieState.movieId.toString()
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllMovieSimilar(movieState: MovieState): Flow<PagingData<MovieSimilar>> {
        val pagingSourceFactory = { detailsDatabase.movieSimilarDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = MovieSimilarRemoteMediator(
                tmdrApi = tmdrApi,
                detailsDatabase = detailsDatabase,
                movieId = movieState.movieId.toString()
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getMovieDetails(movieState: MovieState): MovieDetails {
        return tmdrApi.getAllMovieDetails(movieId = movieState.movieId.toString())
    }

    fun getAllTVReviews(movieState: MovieState): Flow<PagingData<TVReviews>> {
        val pagingSourceFactory = { detailsDatabase.tvReviewsDao().getAllReviews() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TVReviewsRemoteMediator(
                tmdrApi = tmdrApi,
                detailsDatabase = detailsDatabase,
                seriesId = movieState.movieId.toString()
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllTVSimilar(movieState: MovieState): Flow<PagingData<TVSimilar>> {
        val pagingSourceFactory = { detailsDatabase.tvSimilarDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TVSimilarRemoteMediator(
                tmdrApi = tmdrApi,
                detailsDatabase = detailsDatabase,
                seriesId = movieState.movieId.toString()
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getTVDetails(movieState: MovieState): TVDetails {
        return tmdrApi.getAllTVDetails(seriesId = movieState.movieId.toString())
    }

    suspend fun getGuestSessionId(): GuestSession{
        return tmdrApi.getGuestSessionId()
    }

    suspend fun getRequestToken(): RequestToken {
        return tmdrApi.getRequestToken()
    }

    suspend fun getLoginRequestToken(accountState: AccountState): LoginRequestToken {
        return tmdrApi.getLoginRequestToken(userName = accountState.username, password = accountState.password, requestToken = accountState.requestToken.requestToken)
    }

    suspend fun getSessionId(accountState: AccountState): Session {
        return tmdrApi.getSessionId(requestToken = accountState.loginRequestToken.requestToken)
    }

    suspend fun deleteSession(accountState: AccountState) {
        tmdrApi.deleteSession(sessionId = accountState.session.sessionId)
    }

    suspend fun getAccountDetails(accountState: AccountState): AccountDetails {
        return tmdrApi.getAccountDetails(sessionId = accountState.session.sessionId)
    }

    suspend fun addFavourite(accountState: AccountState,movieState: MovieState,mediaType: String) {
        tmdrApi.addFavourite(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, RAW_BODY = AddFavourite(mediaType = mediaType, mediaId = movieState.movieId, favorite = true))
    }

    suspend fun addWatchList(accountState: AccountState,movieState: MovieState,mediaType: String) {
        tmdrApi.addWatchList(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, RAW_BODY = AddWatchlist(mediaType = mediaType, mediaId = movieState.movieId, watchlist = true))
    }

    suspend fun deleteFavourite(accountState: AccountState,movieState: MovieState,mediaType: String) {
        tmdrApi.addFavourite(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, RAW_BODY = AddFavourite(mediaType = mediaType, mediaId = movieState.movieId, favorite = false))
    }

    suspend fun deleteWatchList(accountState: AccountState,movieState: MovieState,mediaType: String) {
        tmdrApi.addWatchList(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, RAW_BODY = AddWatchlist(mediaType = mediaType, mediaId = movieState.movieId, watchlist = false))
    }

    fun getAllFavouriteMovie(accountState: AccountState): Flow<PagingData<FavouriteMovie>> {
        val pagingSourceFactory = { accountDatabase.favouriteMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = FavouriteMovieRemoteMediator(
                tmdrApi = tmdrApi,
                accountDatabase = accountDatabase,
                accountState = accountState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllFavouriteTV(accountState: AccountState): Flow<PagingData<FavouriteTV>> {
        val pagingSourceFactory = { accountDatabase.favouriteTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = FavouriteTVRemoteMediator(
                tmdrApi = tmdrApi,
                accountDatabase = accountDatabase,
                accountState = accountState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllWatchlistMovie(accountState: AccountState): Flow<PagingData<WatchlistMovie>> {
        val pagingSourceFactory = { accountDatabase.watchlistMovieDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = WatchlistMovieRemoteMediator(
                tmdrApi = tmdrApi,
                accountDatabase = accountDatabase,
                accountState = accountState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllWatchlistTV(accountState: AccountState): Flow<PagingData<WatchlistTV>> {
        val pagingSourceFactory = { accountDatabase.watchlistTVDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = WatchlistTVRemoteMediator(
                tmdrApi = tmdrApi,
                accountDatabase = accountDatabase,
                accountState = accountState
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getFavouriteMovieList(accountState: AccountState): Flow<FavouriteMovie> {
        var favouriteListOfMovie: List<FavouriteMovie>
        val flow = flow {
            for(i in 1..(2.0.pow(31.0) -1).toInt()){
                favouriteListOfMovie = tmdrApi.getFavouriteMovies(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = i).results
                if(favouriteListOfMovie.isEmpty()){
                    break
                } else{
                    favouriteListOfMovie.onEach {
                        emit(it)
                    }
                }
            }
        }
        return flow
    }

    suspend fun getFavouriteTVList(accountState: AccountState): Flow<FavouriteTV> {
        var favouriteListOfTV: List<FavouriteTV>
        val flow = flow {
            for(i in 1..(2.0.pow(31.0) -1).toInt()){
                favouriteListOfTV = tmdrApi.getFavouriteTV(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = i).results
                if(favouriteListOfTV.isEmpty()){
                    break
                } else{
                    favouriteListOfTV.onEach {
                        emit(it)
                    }
                }
            }
        }
        return flow
    }

    suspend fun getWatchlistMovieList(accountState: AccountState): Flow<WatchlistMovie> {
        var watchListOfMovie: List<WatchlistMovie>
        val flow = flow {
            for(i in 1..(2.0.pow(31.0) -1).toInt()){
                watchListOfMovie = tmdrApi.getWatchlistMovies(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = i).results
                if(watchListOfMovie.isEmpty()){
                    break
                } else{
                    watchListOfMovie.onEach {
                        emit(it)
                    }
                }
            }
        }
        return flow
    }

    suspend fun getWatchlistTVList(accountState: AccountState): Flow<WatchlistTV> {
        var watchListOfTV: List<WatchlistTV>
        val flow = flow {
            for(i in 1..(2.0.pow(31.0) -1).toInt()){
                watchListOfTV = tmdrApi.getWatchlistTV(accountId = accountState.accountDetails.id, sessionId = accountState.session.sessionId, page = i).results
                if(watchListOfTV.isEmpty()){
                    break
                } else{
                    watchListOfTV.onEach {
                        emit(it)
                    }
                }
            }
        }
        return flow
    }

}