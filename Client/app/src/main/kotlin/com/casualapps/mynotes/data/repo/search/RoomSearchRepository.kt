package com.casualapps.mynotes.data.repo.search

import com.casualapps.mynotes.data.entities.SearchHistoryEntry
import com.casualapps.mynotes.data.database.dao.SearchHistoryDao
import com.casualapps.platform.utils.now
import javax.inject.Inject

class RoomSearchRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchRepository {
    private var userId: Long = 0

    override suspend fun initialize(userId: Long) {
        this.userId = userId
    }

    override suspend fun addSearchTerm(term: String): Long {
        val recentSearches = recentSearches()
        if (recentSearches.contains(term)) {
            return -1
        }
        if (recentSearches.size == 10) {
            searchHistoryDao.removeSearch(recentSearches[9], userId)
        }
        return searchHistoryDao.insertSearch(SearchHistoryEntry(userId, term, now()))
    }

    override suspend fun recentSearches(): List<String> {
        return searchHistoryDao.recentSearches(userId)
    }
}