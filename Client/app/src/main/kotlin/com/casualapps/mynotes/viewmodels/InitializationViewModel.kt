package com.casualapps.mynotes.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.casualapps.mynotes.data.repo.bookmarks.BookmarksRepository
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.data.repo.search.SearchRepository

class InitializationViewModel @ViewModelInject constructor(
        private val notesRepository: NotesRepository,
        private val bookmarksRepository: BookmarksRepository,
        private val searchRepository: SearchRepository
) : ViewModel() {
    suspend fun initializeRepositories(userId: Long) {
        notesRepository.initialize(userId)
        bookmarksRepository.initialize(userId)
        searchRepository.initialize(userId)
    }
}