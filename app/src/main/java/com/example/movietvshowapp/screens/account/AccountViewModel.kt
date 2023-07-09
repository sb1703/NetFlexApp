package com.example.movietvshowapp.screens.account

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.movietvshowapp.data.repository.Repository
import com.example.movietvshowapp.model.account.AccountAvatar
import com.example.movietvshowapp.model.account.AccountDetails
import com.example.movietvshowapp.model.account.TMDB
import com.example.movietvshowapp.model.session.GuestSession
import com.example.movietvshowapp.model.session.LoginRequestToken
import com.example.movietvshowapp.model.session.RequestToken
import com.example.movietvshowapp.model.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: AccountEvent){
        when(event){
            is AccountEvent.UpdatePassword -> {
                _state.update { it.copy(
                    password = event.password
                ) }
            }
            is AccountEvent.UpdateUsername -> {
                _state.update { it.copy(
                    username = event.username
                ) }
            }

            AccountEvent.UpdateIsGuestSession -> {
                _state.update { it.copy(
                    guestSession = repository.getGuestSessionId()
                ) }
            }

            AccountEvent.UpdateAccountDetails -> {
                _state.update { it.copy(
                    accountDetails = repository.getAccountDetails(state.value)
                ) }
            }
            AccountEvent.UpdateLoginRequestToken -> {
                _state.update { it.copy(
                    loginRequestToken = repository.getLoginRequestToken(state.value)
                ) }
            }
            AccountEvent.UpdateRequestToken -> {
                _state.update { it.copy(
                    requestToken = repository.getRequestToken()
                ) }
            }
            AccountEvent.UpdateSession -> {
                _state.update { it.copy(
                    session = repository.getSessionId(state.value)
                ) }
            }

            AccountEvent.UpdateIsLoggedInTrue -> {
                _state.update { it.copy(
                    isLoggedIn = true
                ) }
            }

            AccountEvent.UpdateIsLoggedInFalse -> {
                _state.update { it.copy(
                    isLoggedIn = false
                ) }
            }

            AccountEvent.UpdateIsGuestLoggedInFalse -> {
                _state.update { it.copy(
                    isGuestLoggedIn = false
                ) }
            }
            AccountEvent.UpdateIsGuestLoggedInTrue -> {
                _state.update { it.copy(
                    isGuestLoggedIn = true
                ) }
            }

            is AccountEvent.UpdateListType -> {
                _state.update { it.copy(
                    listType = event.listType
                ) }
            }

            AccountEvent.UpdateGetFavouriteMovie -> {
                _state.update { it.copy(
                    getFavouriteMovie = repository.getAllFavouriteMovie(state.value)
                ) }
            }
            AccountEvent.UpdateGetFavouriteTV -> {
                _state.update { it.copy(
                    getFavouriteTV = repository.getAllFavouriteTV(state.value)
                ) }
            }
            AccountEvent.UpdateGetWatchlistMovie -> {
                _state.update { it.copy(
                    getWatchlistMovie = repository.getAllWatchlistMovie(state.value)
                ) }
            }
            AccountEvent.UpdateGetWatchlistTV -> {
                _state.update { it.copy(
                    getWatchlistTV = repository.getAllWatchlistTV(state.value)
                ) }
            }

            AccountEvent.SetPasswordVisibilityFalse -> {
                _state.update { it.copy(
                    passwordVisibility = false
                ) }
            }
            AccountEvent.SetPasswordVisibilityTrue -> {
                _state.update { it.copy(
                    passwordVisibility = true
                ) }
            }

            AccountEvent.DeleteSession -> {
                repository.deleteSession(state.value)
                _state.update { it.copy(
                    username = "",
                    password = "",
                    isLoggedIn = false,
                    isGuestLoggedIn = false,
                    requestToken = RequestToken(success = false, requestToken = "", expiresAt = ""),
                    loginRequestToken = LoginRequestToken(success = false, requestToken = "", expiresAt = ""),
                    session = Session(success = false, sessionId = ""),
                    guestSession = GuestSession(success = false, guestSessionId = "", expiresAt = ""),
                    accountDetails = AccountDetails(avatar = AccountAvatar(tmdb = TMDB(avatarPath = null)), id = 0, name = "", userName = ""),
                    listType = ListType.NONE,
                    passwordVisibility = false,
                    getWatchlistTV = null,
                    getWatchlistMovie = null,
                    getFavouriteTV = null,
                    getFavouriteMovie = null
                ) }
            }
        }
    }
}