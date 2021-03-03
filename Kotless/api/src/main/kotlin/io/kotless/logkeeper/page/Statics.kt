package io.kotless.logkeeper.page

import io.kotless.MimeType
import io.kotless.dsl.lang.http.StaticGet
import java.io.File

@StaticGet("/css/logkeeper.css", MimeType.CSS)
val siteCss = File("css/logkeeper.css")
