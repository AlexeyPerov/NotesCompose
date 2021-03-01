package com.casualapps.platform.prefs

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import javax.inject.Singleton
import kotlinx.coroutines.flow.first

@Singleton
class AppPrefs constructor(private val store: DataStore<Preferences>) {
    suspend fun setLong(key: String, value: Long) {
        store.edit {
            it[preferencesKey<Long>(key)] = value
        }
    }

    suspend fun getLong(key: String, defaultValue: Long = 0): Long {
        return store.data.first()[preferencesKey<Long>(key)] ?: defaultValue
    }
}
