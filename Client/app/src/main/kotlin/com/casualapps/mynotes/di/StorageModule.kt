package com.casualapps.mynotes.di

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import com.casualapps.platform.prefs.AppPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object StorageModule {
    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, com.casualapps.mynotes.data.database.AppDB::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideUserDao(appDB: com.casualapps.mynotes.data.database.AppDB) = appDB.userDao

    @Provides
    @Singleton
    fun provideNotesDao(appDB: com.casualapps.mynotes.data.database.AppDB) = appDB.notesDao

    @Provides
    @Singleton
    fun provideCategoryDao(appDB: com.casualapps.mynotes.data.database.AppDB) = appDB.categoriesDao

    @Provides
    @Singleton
    fun provideBookmarkDao(appDB: com.casualapps.mynotes.data.database.AppDB) = appDB.bookmarkDao

    @Provides
    @Singleton
    fun provideRecentSearchDao(appDB: com.casualapps.mynotes.data.database.AppDB) = appDB.searchHistoryDao

    @Provides
    @Singleton
    fun provideAppPrefs(@ApplicationContext context: Context) = AppPrefs(context.createDataStore(name = "app_prefs"))
}
