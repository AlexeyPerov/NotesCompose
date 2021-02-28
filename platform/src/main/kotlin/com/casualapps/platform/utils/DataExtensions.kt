package com.casualapps.platform.utils

fun <T> PagedResponse<T?>.toData(data: List<T> = emptyList()) =
    LatestData<T>((data + results).filterNotNull())
