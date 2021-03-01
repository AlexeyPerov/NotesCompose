package com.casualapps.mynotes.data.repo.bookmarks

import com.casualapps.mynotes.data.entities.Bookmark

interface BookmarksRepository {
    suspend fun initialize(userId: Long)
    suspend fun fetchBookmarks(): List<Bookmark>
    suspend fun addCategoryToBookmarks(categoryId: String): Long
    suspend fun removeCategoryFromBookmarks(categoryId: String): Int
    suspend fun isInBookmarks(categoryId: String): Boolean
}