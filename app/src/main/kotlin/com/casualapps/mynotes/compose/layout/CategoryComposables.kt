package com.casualapps.mynotes.compose.layout

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.mynotes.ui.primaryColor

@Composable
fun CategoryGridCard(category: Category, onClick: () -> Unit) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        val padding = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        Text(
            text = category.name,
            style = MaterialTheme.typography.h4,
            maxLines = 2,
            modifier = padding
        )
    }
}

@Composable
fun CategoryRowAddCard(onClick: (title: String) -> Unit) {
    val titleState = remember { mutableStateOf("") }

    Card(
        backgroundColor = Color.White,
        elevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable {
            if (titleState.value.isNotEmpty()) {
                onClick(titleState.value)
                titleState.value = ""
            }
        },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier
            .width(100.dp)
            .height(80.dp)) {

            Icon(Icons.Filled.Add)

            HeightSpacer(1)

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
        }
    }

    WidthSpacer(value = 4)
}

@Composable
fun CategoryRowCard(category: Category, isSelected: Boolean,
                    modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        backgroundColor = if (isSelected) primaryColor else Color.White,
        elevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = modifier
            .width(100.dp)
            .height(80.dp)
            .padding(vertical = 8.dp)
        ) {
            val padding = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                modifier = padding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CategoryRowCard(category = Category("", "AoC Pre Prod", 0), isSelected = false, onClick = {  })
}