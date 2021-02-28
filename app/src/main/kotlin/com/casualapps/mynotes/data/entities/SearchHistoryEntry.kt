package com.casualapps.mynotes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntry(
        val userId: Long,
        val term: String,
        val timeStamp: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
)