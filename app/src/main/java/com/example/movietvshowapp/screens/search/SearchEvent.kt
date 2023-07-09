package com.example.movietvshowapp.screens.search

sealed interface SearchEvent {
    data class UpdateSearchTextState(val searchTextState: String): SearchEvent
    object UpdateGetSearchMovie: SearchEvent
    object UpdateGetSearchTV: SearchEvent
    object UpdateGetSearchPerson: SearchEvent
    data class UpdateSearchType(val searchTYpe: SearchType): SearchEvent
}