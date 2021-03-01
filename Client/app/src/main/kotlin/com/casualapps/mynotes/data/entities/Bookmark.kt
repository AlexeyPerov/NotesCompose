package com.casualapps.mynotes.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "category_bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("user_id")
    val userId: Long
) : Parcelable