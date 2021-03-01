package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.mynotes.data.entities.NoteContents
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.data.repo.search.SearchRepository
import com.casualapps.mynotes.enums.ContentState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CategoriesViewModel @ViewModelInject constructor(
    private val notesRepository: NotesRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _categoriesState = mutableStateOf(ContentState.IDLE)
    val categoriesState: State<ContentState>
        get() = _categoriesState

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>>
        get() = _categoriesLiveData

    private val _notesState = mutableStateOf(ContentState.IDLE)
    val notesState: State<ContentState>
        get() = _notesState

    private val _notesLiveData = MutableLiveData<List<Note>>()
    val notesLiveData: LiveData<List<Note>>
        get() = _notesLiveData

    private val _selectedCategoryId = mutableStateOf("")
    val selectedCategoryId: State<String>
        get() = _selectedCategoryId

    init {
        fetchCategories()
    }

    fun addCategory(title: String) = viewModelScope.launch {
        notesRepository.addCategory(title)
        fetchCategories()
    }

    fun addNote(title: String, contents: String) = viewModelScope.launch {
        notesRepository.addNote(categoryId = _selectedCategoryId.value,
                contents = NoteContents(title = title, contents = contents, archived = false))
        fetchNotes(_selectedCategoryId.value)
    }

    fun selectCategory(categoryId: String) {
        _selectedCategoryId.value = categoryId
        fetchNotes(categoryId = categoryId)
    }

    private fun fetchCategories() = viewModelScope.launch {
        _categoriesState.value = ContentState.LOADING

        when (val result = notesRepository.fetchCategories()) {
            is Success -> {
                _categoriesLiveData.value = result.data.categories
                _categoriesState.value = ContentState.DATA

                if (!result.data.categories.any { c -> c.id == _selectedCategoryId.value }) {
                    if (result.data.categories.isNotEmpty()) {
                        selectCategory(result.data.categories.first().id)
                    } else {
                        _selectedCategoryId.value = ""
                    }
                }
            }
            is Failure -> {
                _categoriesState.value = ContentState.ERROR
                _categoriesLiveData.value = emptyList()
                _selectedCategoryId.value = ""
            }
        }
    }

    private fun fetchNotes(categoryId: String) = viewModelScope.launch {
        _notesState.value = ContentState.LOADING

        when (val result = notesRepository.fetchNotes(categoryId = categoryId)) {
            is Success -> {
                _notesLiveData.value = result.data.notes
                _notesState.value = ContentState.DATA
            }
            is Failure -> {
                _notesState.value = ContentState.ERROR
                _notesLiveData.value = emptyList()
            }
        }
    }
}
