package com.example.movietvshowapp.screens.account


sealed interface AccountEvent {
    data class UpdateUsername(val username: String): AccountEvent
    data class UpdatePassword(val password: String): AccountEvent
    object UpdateRequestToken: AccountEvent
    object UpdateLoginRequestToken: AccountEvent
    object UpdateSession: AccountEvent
    object DeleteSession: AccountEvent
    object UpdateAccountDetails: AccountEvent
    object UpdateIsGuestSession: AccountEvent
    object UpdateIsLoggedInTrue: AccountEvent
    object UpdateIsLoggedInFalse: AccountEvent
    object UpdateIsGuestLoggedInTrue: AccountEvent
    object UpdateIsGuestLoggedInFalse: AccountEvent
    data class UpdateListType(val listType: ListType): AccountEvent
    object UpdateGetFavouriteMovie: AccountEvent
    object UpdateGetFavouriteTV: AccountEvent
    object UpdateGetWatchlistMovie: AccountEvent
    object UpdateGetWatchlistTV: AccountEvent
    object SetPasswordVisibilityFalse: AccountEvent
    object SetPasswordVisibilityTrue: AccountEvent
}