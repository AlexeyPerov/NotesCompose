package com.casualapps.mynotes.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Note(
    @PrimaryKey()
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("archived")
    val archived: Boolean,

    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("category_id")
    val categoryId: String
) : Parcelable