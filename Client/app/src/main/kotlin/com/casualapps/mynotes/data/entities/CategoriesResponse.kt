package com.casualapps.mynotes.data.entities

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(@SerializedName("categories") val categories: List<Category>)