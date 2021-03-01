package com.casualapps.mynotes.data.repo.bookmarks

import com.casualapps.mynotes.data.entities.Bookmark
import com.casualapps.mynotes.data.database.dao.BookmarkDao
import javax.inject.Inject

class RoomBookmarksRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarksRepository {
    private var userId: Long = 0

    override suspend fun initialize(userId: Long) {
        this.userId = userId
    }

    override suspend fun addCategoryToBookmarks(categoryId: String): Long {
        return bookmarkDao.addToBookmarks(Bookmark(userId = userId, categoryId = categoryId))
    }

    override suspend fun isInBookmarks(categoryId: String): Boolean {
        return bookmarkDao.isInBookmarks(userId = userId, categoryId = categoryId) != null
    }

    override suspend fun removeCategoryFromBookmarks(categoryId: String): Int {
        return bookmarkDao.removeFromBookmarks(userId, categoryId)
    }

    override suspend fun fetchBookmarks(): List<Bookmark> {
        return bookmarkDao.fetchBookmarks(userId)
    }
}