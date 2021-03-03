package com.casualapps.mynotes.data.repo.notes.retrofit

import com.casualapps.mynotes.data.entities.*
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.PagedResponse
import javax.inject.Inject

class RetrofitNotesRepository @Inject constructor(
        private val notesApi: NotesApi
) : NotesRepository {
    private var _userId: Long = 0

    override suspend fun initialize(userId: Long) {
        _userId = userId
    }

    override suspend fun fetchCategories(): ApiResult<CategoriesResponse> =
            handleApi({ notesApi.categories(userId = _userId) })

    override suspend fun fetchNotes(categoryId: String): ApiResult<NotesResponse> =
            handleApi({ notesApi.notes(userId = _userId, categoryId = categoryId) })

    override suspend fun search(query: String, page: Int): ApiResult<PagedResponse<Note?>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(
            categoryId: String,
            contents: NoteContents
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(
            categoryId: String,
            noteId: String,
            contents: NoteContents
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun removeNote(categoryId: String, noteId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun archiveNote(
            categoryId: String,
            noteId: String,
            archive: Boolean
    ) {
        TODO("Not yet implemented")
    }
}