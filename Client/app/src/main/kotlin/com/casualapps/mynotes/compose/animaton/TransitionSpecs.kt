package com.casualapps.mynotes.compose.animaton

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec

val tweenSpec = TweenSpec<Float>(durationMillis = 400, delay = 100, easing = LinearOutSlowInEasing)