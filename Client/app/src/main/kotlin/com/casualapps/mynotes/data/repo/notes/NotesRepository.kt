package com.casualapps.mynotes.data.repo.notes

import com.casualapps.mynotes.data.entities.*
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.PagedResponse

interface NotesRepository {
    suspend fun initialize(userId: Long)

    suspend fun fetchCategories(): ApiResult<CategoriesResponse>
    suspend fun addCategory(name: String)

    suspend fun fetchNotes(categoryId: String): ApiResult<NotesResponse>
    suspend fun addNote(categoryId: String, contents: NoteContents)
    suspend fun updateNote(categoryId: String, noteId: String, contents: NoteContents)
    suspend fun removeNote(categoryId: String, noteId: String)
    suspend fun archiveNote(categoryId: String, noteId: String, archive: Boolean)

    suspend fun search(query: String, page: Int): ApiResult<PagedResponse<Note?>>
}

