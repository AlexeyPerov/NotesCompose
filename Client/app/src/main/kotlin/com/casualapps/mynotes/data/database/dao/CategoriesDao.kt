package com.casualapps.mynotes.data.database.dao

import androidx.room.*
import com.casualapps.mynotes.data.entities.Category

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(category: Category): Long

    @Query("SELECT * FROM category WHERE userId = :userId")
    suspend fun fetch(userId: Long): List<Category>

    @Query("DELETE FROM category WHERE userId =:userId AND id = :categoryId")
    suspend fun remove(userId: Long, categoryId: String): Int
}