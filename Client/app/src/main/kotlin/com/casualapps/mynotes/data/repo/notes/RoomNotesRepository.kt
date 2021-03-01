package com.casualapps.mynotes.data.repo.notes

import com.casualapps.mynotes.data.entities.*
import com.casualapps.mynotes.data.database.dao.CategoriesDao
import com.casualapps.mynotes.data.database.dao.NotesDao
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.PagedResponse
import com.casualapps.platform.utils.Success
import java.util.*
import javax.inject.Inject

class RoomNotesRepository @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val notesDao: NotesDao
) : NotesRepository {
    private var userId: Long = 0

    override suspend fun initialize(userId: Long) {
        this.userId = userId;
    }

    override suspend fun fetchCategories(): ApiResult<CategoriesResponse> {
        return Success(CategoriesResponse(categoriesDao.fetch(userId = userId)))
    }

    override suspend fun addCategory(name: String) {
        categoriesDao.add(Category(id = generateUUID(), name = name, userId = userId))
    }

    override suspend fun fetchNotes(categoryId: String): ApiResult<NotesResponse> {
        return Success(NotesResponse(notesDao.fetchNotes(userId = userId, categoryId = categoryId)))
    }

    override suspend fun addNote(
        categoryId: String,
        contents: NoteContents
    ) {
        notesDao.add(Note(id = generateUUID(), title = contents.title, contents = contents.contents,
                archived = contents.archived, categoryId = categoryId, userId = userId))
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

    override suspend fun search(query: String, page: Int): ApiResult<PagedResponse<Note?>> {
        TODO("Not yet implemented")
    }

    private fun generateUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}
