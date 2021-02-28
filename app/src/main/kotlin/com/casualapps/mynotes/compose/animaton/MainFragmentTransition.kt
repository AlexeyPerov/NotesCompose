package com.casualapps.mynotes.compose.animaton

import android.annotation.SuppressLint
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import com.casualapps.mynotes.compose.animaton.LoginEntryTransitionState.END
import com.casualapps.mynotes.compose.animaton.LoginEntryTransitionState.START

val alphaPropKey = FloatPropKey(label = "Alpha")

enum class LoginEntryTransitionState{
    START, END
}

@SuppressLint("Range")
val mainFragmentTransition = transitionDefinition<LoginEntryTransitionState> {
    state(START){
        this[alphaPropKey] = 0F
    }
    state(END){
        this[alphaPropKey] = 1F
    }

    transition {
        alphaPropKey using tween(durationMillis = 500, delayMillis = 800, easing = LinearOutSlowInEasing)
    }
}