package com.casualapps.mynotes.compose.layout

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.casualapps.platform.extensions.matchParent
import com.casualapps.mynotes.ui.primaryColor

@Composable
fun CardView(modifier: Modifier, content: @Composable () -> Unit) = Card(elevation = 4.dp,
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small) {
    Box(alignment = Alignment.CenterStart) {
        content()
    }
}

@Composable
fun CardButton(text: String, icon: Int = -1, modifier: Modifier = Modifier, height: Int = 50, onClick: () -> Unit) =
        CardView(modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick).height(height.dp)) {
            WithConstraints {
                Box(modifier.matchParent().background(primaryColor)) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.matchParent().padding(16.dp, 0.dp)) {
                        if (icon > 0) {
                            Image(asset = vectorResource(id = icon), modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(Color.White))
                        }
                        Text(text = text, modifier = Modifier.fillMaxWidth().offset(x = if (icon > 0) (-10).dp else 0.dp),
                            color = Color.White, textAlign = TextAlign.Center, style = MaterialTheme.typography.button)
                    }
                }
            }

        }

@Composable
fun WidthSpacer(value: Int) = Spacer(modifier = Modifier.preferredWidth(value.dp))

@Composable
fun HeightSpacer(value: Int) = Spacer(modifier = Modifier.preferredHeight(value.dp))

@Composable
fun LoadingView(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally))
    }
}

@Composable
fun ErrorView(message: String = "Something went wrong"){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(asset = vectorResource(id = com.casualapps.mynotes.R.drawable.ic_round_warning_24),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.error),
        modifier = Modifier.size(96.dp))
        HeightSpacer(value = 12)
        ErrorText(message)
    }
}

@Composable
fun EmptyView(message: String = ""){
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
    ) {
        ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
            EmptyText(message)
        }
    }
}

@Composable
fun Toast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(ContextAmbient.current, message, length).show()
}