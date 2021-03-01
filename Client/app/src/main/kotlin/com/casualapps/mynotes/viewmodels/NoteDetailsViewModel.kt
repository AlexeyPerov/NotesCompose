package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.enums.ContentState

class NoteViewModel @ViewModelInject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    private var _categoryId: String = "";

    private val _pageState = mutableStateOf(ContentState.IDLE)
    val contentState: State<ContentState> get() = _pageState

    private val _noteLiveData = MutableLiveData<Note>()
    val noteLiveData: LiveData<Note> get() = _noteLiveData

    fun setNote(categoryId: String, note: Note) {
        _categoryId = categoryId
        _noteLiveData.postValue(note)
        _pageState.value = ContentState.DATA
    }
}
