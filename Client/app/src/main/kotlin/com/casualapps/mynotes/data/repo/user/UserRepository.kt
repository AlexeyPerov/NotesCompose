package com.casualapps.mynotes.data.repo.user

import com.casualapps.mynotes.data.entities.User

interface UserRepository {
    suspend fun insertUser(user: User): Long
    suspend fun isValidUser(user: User): Long
    suspend fun userExists(user: User): Boolean
    suspend fun fetchUser(id: Long): User?
}