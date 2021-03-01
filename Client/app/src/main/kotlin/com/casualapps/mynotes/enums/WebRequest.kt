package com.casualapps.mynotes.enums

enum class WebRequest(val key: Int, val url: String, val title: String) {
    ADMIN_PANEL(1, "https://firebase.google.com/", "Admin Panel"),
    SOURCE_CODE(2, "https://github.com/AlexeyPerov/NotesCompose", "GitHub Source");

    companion object {
        operator fun get(key: Int): WebRequest {
            return when (key) {
                ADMIN_PANEL.key -> ADMIN_PANEL
                SOURCE_CODE.key -> SOURCE_CODE
                else -> ADMIN_PANEL
            }
        }
    }
}
