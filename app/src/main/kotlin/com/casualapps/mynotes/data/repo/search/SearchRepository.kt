package com.casualapps.mynotes.data.repo.search

interface SearchRepository {
    suspend fun initialize(userId: Long)
    suspend fun addSearchTerm(term: String): Long
    suspend fun recentSearches(): List<String>
}