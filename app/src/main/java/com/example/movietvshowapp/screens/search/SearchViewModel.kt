package com.example.movietvshowapp.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.movietvshowapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.UpdateSearchTextState -> {
                _state.update { it.copy(
                    searchTextState = event.searchTextState
                ) }
            }
            SearchEvent.UpdateGetSearchMovie -> {
                _state.update { it.copy(
                    getSearchMovie = repository.getAllSearchMovieImages(state.value)
                ) }
                Log.d("searchType",repository.getAllSearchMovieImages(state.value).toString() + "ViewModel")
            }
            SearchEvent.UpdateGetSearchPerson -> {
                _state.update { it.copy(
                    getSearchPerson = repository.getAllSearchPersonImages(state.value)
                ) }
            }
            SearchEvent.UpdateGetSearchTV -> {
                _state.update { it.copy(
                    getSearchTV = repository.getAllSearchTVImages(state.value)
                ) }
            }
            is SearchEvent.UpdateSearchType -> {
                _state.update { it.copy(
                    searchType = event.searchTYpe
                ) }
            }
        }
    }

}
