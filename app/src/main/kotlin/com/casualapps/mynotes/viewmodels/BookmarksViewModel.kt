package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.Bookmark
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import com.casualapps.mynotes.data.repo.bookmarks.BookmarksRepository
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.enums.ContentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarksViewModel @ViewModelInject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _contentState = mutableStateOf(ContentState.IDLE)
    val contentState: State<ContentState>
        get() = _contentState

    private val _bookmarksStateFlow = MutableStateFlow(emptyList<Category>())
    val categoriesStateFlow: StateFlow<List<Category>>
        get() = _bookmarksStateFlow

    fun fetchBookmarks() = viewModelScope.launch {
        _contentState.value = ContentState.LOADING
        val result = bookmarksRepository.fetchBookmarks()
        if (result.isEmpty()) {
            _contentState.value = ContentState.EMPTY
        } else {
            _contentState.value = ContentState.DATA
        }

        fetchCategories(result)
    }

    fun removeFromBookmarks(categoryId: String) = viewModelScope.launch {
        val result = bookmarksRepository.removeCategoryFromBookmarks(categoryId)
        if (result > 0) {
            fetchBookmarks()
        }
    }

    private fun fetchCategories(bookmarks: List<Bookmark>) = viewModelScope.launch {
        when (val result = notesRepository.fetchCategories()) {
            is Success -> {
                _contentState.value = ContentState.DATA
                _bookmarksStateFlow.value = result.data.categories
                    .filter { category -> bookmarks.any { bookmark -> bookmark.categoryId == category.id  } }
            }
            is Failure -> {
                _contentState.value = ContentState.ERROR
            }
        }
    }
}
