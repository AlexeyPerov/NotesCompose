package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import com.casualapps.mynotes.data.repo.bookmarks.BookmarksRepository
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.enums.ContentState
import kotlinx.coroutines.launch

class CategoryDetailViewModel @ViewModelInject constructor(
    private val bookmarkRepository: BookmarksRepository,
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _bookmarksListLiveData = MutableLiveData<Boolean>()
    val bookmarksLiveData: LiveData<Boolean> get() = _bookmarksListLiveData

    private val _pageState = mutableStateOf(ContentState.IDLE)
    val contentState: State<ContentState> get() = _pageState

    private val _notesLiveData = MutableLiveData<List<Note>>()
    val notesLiveData: LiveData<List<Note>> get() = _notesLiveData

    fun updateBookmarkStatus(category: Category) = viewModelScope.launch {
        _bookmarksListLiveData.postValue(bookmarkRepository.isInBookmarks(category.id))
    }

    fun addToBookmarks(category: Category) = viewModelScope.launch {
        val result = bookmarkRepository.addCategoryToBookmarks(category.id)
        if (result > -1) {
            _bookmarksListLiveData.postValue(true)
        }
    }

    fun removeFromBookmarks(category: Category) = viewModelScope.launch {
        if (bookmarkRepository.removeCategoryFromBookmarks(category.id) > 0)
            _bookmarksListLiveData.postValue(false)
    }

    fun updateNotes(category: Category) = viewModelScope.launch {
        _pageState.value = ContentState.LOADING
        when (val result = notesRepository.fetchNotes(categoryId = category.id)) {
            is Success -> {
                _pageState.value = ContentState.DATA
                _notesLiveData.postValue(result.data.notes)
            }
            is Failure -> {
                _pageState.value = ContentState.ERROR
            }
        }
    }
}
