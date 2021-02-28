package com.casualapps.mynotes.data.repo.notes

import com.casualapps.mynotes.data.entities.*
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.PagedResponse
import com.casualapps.platform.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesMockRepository @Inject constructor(
) : NotesRepository {
    private var _initialized = false

    override suspend fun initialize(userId: Long) {
        if (!_initialized) {
            delay(2000)
            _initialized = true
        }
    }

    override suspend fun fetchCategories(): ApiResult<CategoriesResponse> {
        return withContext(Dispatchers.IO) {
            delay(100)
            Success(CategoriesResponse(createListOfRandomMockCategories(10)))
        }
    }

    override suspend fun search(query: String, page: Int): ApiResult<PagedResponse<Note?>> {
        return withContext(Dispatchers.IO) {
            delay(100)
            Success(PagedResponse(0, 1, createListOfRandomMockNotes(2, "")))
        }
    }

    override suspend fun fetchNotes(categoryId: String): ApiResult<NotesResponse> {
        return withContext(Dispatchers.IO) {
            delay(100)
            Success(NotesResponse(createListOfRandomMockNotes(20, categoryId)))
        }
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

    private fun createListOfRandomMockCategories(count: Int): List<Category> {
        val result = mutableListOf<Category>()
        for (i in 1..count) {
            result.add(createRandomMockCategory(i))
        }
        return result
    }

    private fun createRandomMockCategory(id: Int): Category {
        return Category(
            id = id.toString(),
            name = "Category $id",
            userId = 0
        )
    }

    private fun createListOfRandomMockNotes(count: Int, categoryId: String): List<Note> {
        val result = mutableListOf<Note>()
        for (i in 1..count) {
            result.add(createRandomMockNote(i, categoryId))
        }
        return result
    }

    private fun createRandomMockNote(id: Int, categoryId: String): Note {
        return Note(id.toString(), "title $id", "contents $id", false, 0, categoryId)
    }
}