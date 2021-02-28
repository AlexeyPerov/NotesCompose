package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.enums.ContentState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CategoriesListViewModel @ViewModelInject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    private val _categoriesStateFlow = MutableStateFlow(emptyList<Category>())
    val categoriesStateFlow: StateFlow<List<Category>>
        get() = _categoriesStateFlow

    private val _state = mutableStateOf(ContentState.IDLE)
    val state: State<ContentState>
        get() = _state

    fun fetchCategories() = viewModelScope.launch {

        when (val result = notesRepository.fetchCategories()) {
            is Success -> {
                _state.value = ContentState.DATA
                _categoriesStateFlow.value = result.data.categories
            }
            is Failure -> {
                _state.value = ContentState.ERROR
            }
        }
    }
}
