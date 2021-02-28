package com.casualapps.mynotes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.casualapps.mynotes.data.entities.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarks(bookmark: Bookmark): Long

    @Query("SELECT * FROM category_bookmark WHERE userId = :userId")
    suspend fun fetchBookmarks(userId: Long): List<Bookmark>

    @Query("DELETE FROM category_bookmark WHERE userId =:userId AND categoryId = :categoryId")
    suspend fun removeFromBookmarks(userId: Long, categoryId: String): Int

    @Query("SELECT * FROM category_bookmark WHERE userId = :userId AND categoryId = :categoryId LIMIT 1")
    suspend fun isInBookmarks(userId: Long, categoryId: String): Bookmark?
}