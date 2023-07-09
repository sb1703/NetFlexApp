package com.example.movietvshowapp.screens.search

import androidx.paging.PagingData
import com.example.movietvshowapp.model.search.SearchMovie
import com.example.movietvshowapp.model.search.SearchPerson
import com.example.movietvshowapp.model.search.SearchTV
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchTextState: String = "",
    val getSearchMovie: Flow<PagingData<SearchMovie>>? = null,
    val getSearchTV: Flow<PagingData<SearchTV>>? = null,
    val getSearchPerson: Flow<PagingData<SearchPerson>>? = null,
    val searchType: SearchType = SearchType.SEARCH_MOVIE
)
