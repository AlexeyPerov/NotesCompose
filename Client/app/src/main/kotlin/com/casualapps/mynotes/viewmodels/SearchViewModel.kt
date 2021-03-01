package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import com.casualapps.platform.utils.LatestData
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.data.repo.search.SearchRepository
import com.casualapps.mynotes.enums.ContentState
import com.casualapps.platform.utils.toData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val searchRepository: SearchRepository,
    private val noteRepository: NotesRepository
) : ViewModel() {

    private val _searchShowsStateFlow = MutableStateFlow(LatestData<Note>(emptyList()))
    val searchShowStateFlow: StateFlow<LatestData<Note>>
        get() = _searchShowsStateFlow

    private val _pageState = mutableStateOf(ContentState.IDLE)
    val contentState: State<ContentState>
        get() = _pageState

    private val _recentSearchLiveData = MutableLiveData<List<String>>()
    val recentSearchLiveData: LiveData<List<String>>
        get() = _recentSearchLiveData

    private var page = 1
    private var totalPages = 1
    private var searchQuery = ""

    init {
        viewModelScope.launch {
            _recentSearchLiveData.postValue(searchRepository.recentSearches())
        }
    }

    fun search(query: String) = viewModelScope.launch {
        if (searchQuery != query) {
            _searchShowsStateFlow.value = LatestData(emptyList())
            page = 1
            totalPages = 1
        }
        searchQuery = query
        if (searchRepository.addSearchTerm(query) != -1L) {
            _recentSearchLiveData.postValue(searchRepository.recentSearches())
        }
        if (page == 1) {
            _pageState.value = ContentState.LOADING
        }
        when (val result = noteRepository.search(page = page, query = query)) {
            is Success -> {
                totalPages = result.data.totalPages
                if (page == 1) {
                    if (result.data.results.isNotEmpty()) {
                        _pageState.value = ContentState.DATA
                    } else {
                        _pageState.value = ContentState.EMPTY
                    }
                }
                page++
                _searchShowsStateFlow.value = result.data.toData(_searchShowsStateFlow.value.data)
            }
            is Failure -> {
                _pageState.value = ContentState.ERROR
            }
        }
    }

    fun nextPage() {
        if (page <= totalPages) {
            search(searchQuery)
        }
    }

    fun idle() {
        _searchShowsStateFlow.value = LatestData(emptyList())
        page = 1
        totalPages = 1
        _pageState.value = ContentState.IDLE
    }
}
