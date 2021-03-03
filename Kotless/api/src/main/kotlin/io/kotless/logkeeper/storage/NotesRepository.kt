package io.kotless.logkeeper.storage

import org.slf4j.LoggerFactory
import kotlinx.serialization.*

object NotesRepository {
    private val logger = LoggerFactory.getLogger(NotesRepository::class.java)

    fun getCategories(userId: String): List<Category> {
        return createListOfRandomMockCategories(10)
    }

    fun getNotes(userId: String, categoryId: String): List<Note> {
        return createListOfRandomMockNotes(10, categoryId)
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

@Serializable
data class Category (
    val id: String,
    val userId: Long,
    val name: String
)

@Serializable
data class Note (
    val id: String,
    val title: String,
    val contents: String,
    val archived: Boolean,
    val userId: Long,
    val categoryId: String
)