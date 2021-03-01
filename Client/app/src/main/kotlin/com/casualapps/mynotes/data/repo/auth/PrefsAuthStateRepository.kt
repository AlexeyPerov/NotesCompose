package com.casualapps.mynotes.data.repo.auth

import com.casualapps.platform.prefs.AppPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsAuthStateRepository @Inject constructor(private val appPrefs: AppPrefs) :
    AuthStateRepository {
    companion object {
        private const val USER_ID_KEY = "user_id"
    }

    override suspend fun login(userId: Long) {
        appPrefs.setLong(USER_ID_KEY, userId)
    }

    override suspend fun getLoggedInUser(): Long {
        return appPrefs.getLong(USER_ID_KEY, -1L)
    }

    override suspend fun logout() {
        appPrefs.setLong(USER_ID_KEY, -1L)
    }
}