package com.casualapps.platform.utils

data class PagedResponse<T>(
    val currentPage: Int,
    val totalPages: Int,
    val results: List<T>
)
