package com.casualapps.mynotes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.casualapps.mynotes.data.entities.SearchHistoryEntry

@Dao
interface SearchHistoryDao {
    @Insert
    suspend fun insertSearch(searchHistoryEntry: SearchHistoryEntry): Long

    @Query("SELECT term FROM search_history WHERE userId = :userId ORDER BY timeStamp DESC")
    suspend fun recentSearches(userId: Long): List<String>

    @Query("DELETE from search_history WHERE term = :query AND userId = :userId")
    suspend fun removeSearch(query: String, userId: Long): Int
}
