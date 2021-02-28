package com.casualapps.mynotes.data.entities

import com.google.gson.annotations.SerializedName

data class NotesResponse(@SerializedName("notes") val notes: List<Note>)