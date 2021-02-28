package com.casualapps.mynotes.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
        primary = blue200,
        primaryVariant = blue,
        secondary = primaryColor,
        background = Black,
        surface = Black,
        onPrimary = Black,
        onSecondary = White,
        onBackground = White,
        onSurface = White,
        error = errorColor
)

private val LightColorPalette = lightColors(
        primary = blue400,
        primaryVariant = blue,
        secondary = primaryColor,
        background = White,
        surface = White,
        onPrimary = White,
        onSecondary = White,
        onBackground = Black,
        onSurface = DarkGray,
        error = errorColor
)

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
    )
}
