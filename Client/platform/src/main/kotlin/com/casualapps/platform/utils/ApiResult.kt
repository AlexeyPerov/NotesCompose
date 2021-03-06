package com.casualapps.platform.utils

sealed class ApiResult<out t : Any?>
data class Success<out T : Any?>(val data: T) : ApiResult<T>()
data class Failure(val errorMessage: String?) : ApiResult<Nothing>()
