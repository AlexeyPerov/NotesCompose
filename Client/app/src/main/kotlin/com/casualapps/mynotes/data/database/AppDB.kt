package com.casualapps.mynotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.casualapps.mynotes.data.database.dao.*
import com.casualapps.mynotes.data.entities.*

@Database(entities = [UserEntity::class, Note::class, Category::class, Bookmark::class, SearchHistoryEntry::class],
    version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val categoriesDao: CategoriesDao
    abstract val notesDao: NotesDao
    abstract val bookmarkDao: BookmarkDao
    abstract val searchHistoryDao: SearchHistoryDao
}
