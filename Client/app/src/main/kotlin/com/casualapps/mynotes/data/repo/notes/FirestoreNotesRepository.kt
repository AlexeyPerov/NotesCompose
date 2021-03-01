package com.casualapps.mynotes.data.repo.notes

import com.casualapps.mynotes.data.entities.*
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.PagedResponse
import com.casualapps.platform.utils.Success
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreNotesRepository @Inject constructor() : NotesRepository {
    private lateinit var _user: DocumentReference
    private var _userId: Long = 0

    override suspend fun initialize(userId: Long) {
        val db = FirebaseFirestore.getInstance()
        val users = db.collection("users");
        _user = users.document("default");

        _userId = userId
    }

    override suspend fun fetchCategories(): ApiResult<CategoriesResponse> {
        val querySnapshot = _user.collection("categories").get().await();

        var result: MutableList<Category> = mutableListOf()

        for (document in querySnapshot) {
            val name = document.data["name"].toString()
            result.add(
                Category(
                    id = document.id,
                    name = name,
                    userId = _userId
            ))
        }

        return Success(CategoriesResponse(result))
    }

    override suspend fun addCategory(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotes(categoryId: String): ApiResult<NotesResponse> {
        val category = _user.collection("categories").document(categoryId)
        val notes = category.collection("notes").get().await()

        var result: MutableList<Note> = mutableListOf()

        for (document in notes) {
            val title = document.data["title"].toString()
            val contents = document.data["contents"].toString()
            val archived = document.data["archived"] as Boolean;

            result.add(
                Note(
                    id = document.id,
                    title = title,
                    contents = contents,
                    archived = archived,
                    categoryId = categoryId,
                    userId = _userId
                ))
        }

        return Success(NotesResponse(result))
    }

    override suspend fun addNote(categoryId: String, contents: NoteContents) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(categoryId: String, noteId: String, contents: NoteContents) {
        TODO("Not yet implemented")
    }

    override suspend fun removeNote(categoryId: String, noteId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun archiveNote(categoryId: String, noteId: String, archive: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun search(query: String, page: Int): ApiResult<PagedResponse<Note?>> {
        TODO("Not yet implemented")
    }

}