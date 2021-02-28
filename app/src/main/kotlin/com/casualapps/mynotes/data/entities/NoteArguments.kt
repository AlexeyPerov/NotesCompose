package com.casualapps.mynotes.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteArguments (
    val categoryId: String,
    val note: Note
) : Parcelable