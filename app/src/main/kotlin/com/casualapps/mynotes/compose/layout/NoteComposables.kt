package com.casualapps.mynotes.compose.layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.casualapps.platform.extensions.matchParent

@Composable
fun NoteCard(title: String, contents: String, onClick: () -> Unit){
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                if (contents.isNotEmpty()) {
                    HeightSpacer(4)
                    ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
                        Text(
                            text = contents,
                            maxLines = 3,
                            style = MaterialTheme.typography.overline,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
                Spacer(Modifier.preferredHeight(8.dp))
            }
    }
}

@Composable
fun NoteAddCard(onClick: (title: String, contents: String) -> Unit){
    val titleState = remember { mutableStateOf("") }
    val contentsState = remember { mutableStateOf("") }
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 8.dp)
            .clickable {
            if (!titleState.value.isNullOrEmpty()) {
                onClick(titleState.value, contentsState.value)
                titleState.value = ""
                contentsState.value = ""
            }},
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 1.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Add)

            Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 1.dp)) {

                OutlinedTextField(
                    textStyle = MaterialTheme.typography.body2,
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    modifier = Modifier.padding(horizontal = 8.dp)
                        .fillMaxWidth().background(
                        color = Color.White,
                        shape = RectangleShape
                    )
                )

                HeightSpacer(2)

                OutlinedTextField(
                    textStyle = MaterialTheme.typography.body2,
                    value = contentsState.value,
                    onValueChange = { contentsState.value = it },
                    modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotePreview() {
    Box(modifier = Modifier.matchParent()) {
        NoteCard("title", "contents") {

        }
    }
}