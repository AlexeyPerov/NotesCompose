package com.casualapps.mynotes.data.repo.notes.retrofit

import com.casualapps.mynotes.data.entities.CategoriesResponse
import com.casualapps.mynotes.data.entities.NotesResponse
import com.casualapps.platform.utils.ApiResult
import com.casualapps.platform.utils.Failure
import com.casualapps.platform.utils.Success
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.JsonSyntaxException
import org.json.JSONObject

interface NotesApi {
    @GET("categories")
    suspend fun categories(@Query("userId") userId: Long): Response<CategoriesResponse>

    @GET("notes")
    suspend fun notes(@Query("userId") userId: Long, @Query("categoryId") categoryId: String): Response<NotesResponse>
}

suspend fun <T : Any> handleApi(
        call: suspend () -> Response<T>,
        errorMessage: String = "Error fetching data"
): ApiResult<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                return Success(it)
            }
        }
        response.errorBody()?.let {
            return try {
                val errorString = it.string()
                val errorObject = JSONObject(errorString)
                Failure(errorObject.getString("status_message") ?: errorMessage)
            } catch (ignored: JsonSyntaxException) {
                Failure(ignored.message)
            }
        }
        return Failure(errorMessage = errorMessage)
    } catch (e: Exception) {
        return Failure(e.message ?: errorMessage)
    }
}