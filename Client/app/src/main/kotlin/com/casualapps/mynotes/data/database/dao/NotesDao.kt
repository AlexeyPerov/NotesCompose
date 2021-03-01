package com.casualapps.mynotes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.casualapps.mynotes.data.entities.Note

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: Note): Long

    @Query("SELECT * FROM note WHERE userId = :userId AND categoryId = :categoryId")
    suspend fun fetchNotes(userId: Long, categoryId: String): List<Note>

    @Query("DELETE FROM note WHERE userId =:userId AND id = :id")
    suspend fun remove(userId: Long, id: String): Int
}