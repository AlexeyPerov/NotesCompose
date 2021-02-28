package com.casualapps.mynotes.compose.layout

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextButton(text: String, modifier: Modifier = Modifier){
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.button)
}

@Composable
fun Link(text: String, modifier: Modifier = Modifier){
    Text(text = text, color = MaterialTheme.colors.primaryVariant,
            modifier = modifier,
            style = MaterialTheme.typography.button, textDecoration = TextDecoration.Underline)
}

@Composable
fun Body2(text: String, modifier: Modifier = Modifier){
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.body2)
}

@Composable
fun H6(text: String,  modifier: Modifier = Modifier){
    Text(text = text, style = MaterialTheme.typography.h6, modifier = modifier)
}

@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier){
    Text(text = text, modifier = modifier.fillMaxWidth().padding(horizontal = 48.dp),
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center, color = MaterialTheme.colors.error.copy(alpha = 0.9F))
}

@Composable
fun EmptyText(text: String, modifier: Modifier = Modifier){
    Text(text = text, modifier = modifier.fillMaxWidth().padding(horizontal = 48.dp),
            style = MaterialTheme.typography.body2,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center)
}