package com.casualapps.mynotes.di

import com.casualapps.mynotes.data.repo.auth.AuthStateRepository
import com.casualapps.mynotes.data.repo.auth.PrefsAuthStateRepository
import com.casualapps.mynotes.data.repo.bookmarks.BookmarksRepository
import com.casualapps.mynotes.data.repo.bookmarks.RoomBookmarksRepository
import com.casualapps.mynotes.data.repo.notes.NotesRepository
import com.casualapps.mynotes.data.repo.notes.RoomNotesRepository
import com.casualapps.mynotes.data.repo.search.RoomSearchRepository
import com.casualapps.mynotes.data.repo.search.SearchRepository
import com.casualapps.mynotes.data.repo.user.RoomUserRepository
import com.casualapps.mynotes.data.repo.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoriesModule {
    @Singleton
    @Binds
    abstract fun bindsUserRepository(userRepository: RoomUserRepository): UserRepository

    @Singleton
    @Binds
    abstract fun bindsNoteRepository(noteRepository: RoomNotesRepository): NotesRepository

    @Singleton
    @Binds
    abstract fun bindsSearchRepository(searchRepository: RoomSearchRepository): SearchRepository

    @Singleton
    @Binds
    abstract fun bindsBookmarkRepository(bookmarkRepository: RoomBookmarksRepository): BookmarksRepository

    @Singleton
    @Binds
    abstract fun bindAuthStateRepository(authStateRepository: PrefsAuthStateRepository): AuthStateRepository
}
