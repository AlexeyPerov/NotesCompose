package com.casualapps.mynotes.data.repo.auth

interface AuthStateRepository {
    suspend fun login(userId: Long)
    suspend fun getLoggedInUser(): Long
    suspend fun logout()
}