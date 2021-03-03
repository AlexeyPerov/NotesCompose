package io.kotless.logkeeper.page

import io.kotless.MimeType
import io.kotless.dsl.lang.http.*
import io.kotless.dsl.model.HttpResponse
import io.kotless.logkeeper.storage.NotesRepository
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("LogKeeper")

@Get("/categories")
fun getCategories(userId: String): HttpResponse {
    logger.info("get categories for $userId")
    val categories = NotesRepository.getCategories(userId)
    val body = Json.encodeToString(categories)
    logger.info("categories:$body")
    return HttpResponse(200, mime = MimeType.JSON, body = body)
}

@Get("/notes")
fun getNotes(userId: String, categoryId: String): HttpResponse {
    logger.info("get notes for $categoryId and $userId")
    val notes = NotesRepository.getNotes(userId, categoryId)
    val body = Json.encodeToString(notes)
    logger.info("notes:$body")
    return HttpResponse(200, mime = MimeType.JSON, body = body)
}