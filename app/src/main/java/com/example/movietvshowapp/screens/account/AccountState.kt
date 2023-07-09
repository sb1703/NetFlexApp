package com.example.movietvshowapp.screens.account

import androidx.paging.PagingData
import com.example.movietvshowapp.model.account.AccountAvatar
import com.example.movietvshowapp.model.account.AccountDetails
import com.example.movietvshowapp.model.account.FavouriteMovie
import com.example.movietvshowapp.model.account.FavouriteTV
import com.example.movietvshowapp.model.account.TMDB
import com.example.movietvshowapp.model.account.WatchlistMovie
import com.example.movietvshowapp.model.account.WatchlistTV
import com.example.movietvshowapp.model.session.GuestSession
import com.example.movietvshowapp.model.session.LoginRequestToken
import com.example.movietvshowapp.model.session.RequestToken
import com.example.movietvshowapp.model.session.Session
import kotlinx.coroutines.flow.Flow

data class AccountState(
    val username: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val isGuestLoggedIn: Boolean = false,
    val requestToken: RequestToken = RequestToken(success = false, requestToken = "", expiresAt = ""),
    val loginRequestToken: LoginRequestToken = LoginRequestToken(success = false, requestToken = "", expiresAt = ""),
    val guestSession: GuestSession = GuestSession(success = false, guestSessionId = "", expiresAt = ""),
    val session: Session = Session(success = false, sessionId = ""),
    val passwordVisibility: Boolean = false,
    val accountDetails: AccountDetails = AccountDetails(avatar = AccountAvatar(tmdb = TMDB(avatarPath = null)), id = 0, name = "", userName = ""),
    val listType: ListType = ListType.NONE,
    val getFavouriteMovie: Flow<PagingData<FavouriteMovie>>? = null,
    val getFavouriteTV: Flow<PagingData<FavouriteTV>>? = null,
    val getWatchlistMovie: Flow<PagingData<WatchlistMovie>>? = null,
    val getWatchlistTV: Flow<PagingData<WatchlistTV>>? = null
)