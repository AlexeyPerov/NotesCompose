package com.casualapps.mynotes.compose.layout

import androidx.compose.animation.animatedFloat
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.AlertDialog
import androidx.compose.material.AmbientElevationOverlay
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.onActive
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.animaton.tweenSpec
import com.casualapps.mynotes.enums.ContentState
import java.util.Locale

@Composable
fun Chip(text: String, icon: Int = -1, onClick: () -> Unit) {
    Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp).clickable(onClick = { onClick() }),
            verticalAlignment = Alignment.CenterVertically
    ) {
        ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
            if (icon != -1) {
                Image(modifier = Modifier.preferredSize(14.dp, 14.dp), asset = vectorResource(id = icon), colorFilter = OnSurfaceTint())
            }
            Spacer(Modifier.preferredWidth(4.dp))
            Body2(text = text)
        }
    }
}

@Composable
fun OnSurfaceTint(): ColorFilter {
    return ColorFilter.tint(MaterialTheme.colors.onSurface)
}

@Composable
fun IconText(text: String, icon: Int = -1, onClick: () -> Unit = {}) {
    Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp).clickable(onClick = { onClick() }),
            verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != -1) {
            Image(modifier = Modifier.preferredSize(16.dp, 16.dp), asset = vectorResource(id = icon), colorFilter = OnSurfaceTint())
        }
        Spacer(Modifier.preferredWidth(8.dp))
        Text(text = text, style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun <T> LazyGridFor(
    items: List<T> = listOf(),
    rows: Int = 3,
    hPadding: Int = 8,
    itemContent: @Composable LazyItemScope.(T, Int) -> Unit
) {
    val animatedSet = remember { mutableSetOf<Int>() }
    val chunkedList = items.chunked(rows)
    LazyColumnForIndexed(items = chunkedList, modifier = Modifier.padding(horizontal = hPadding.dp)) { index, it ->
        if (index == 0) {
            HeightSpacer(value = 8)
        }
        val offsetValue = animatedFloat(initVal = if(index in animatedSet) 0F else 150F)
        val alphaValue = animatedFloat(initVal = if(index in animatedSet) 1F else 0F)
        onActive {
            offsetValue.animateTo(0F, anim = tweenSpec)
            alphaValue.animateTo(1F, anim = tweenSpec)
            animatedSet.add(index)
        }
        onDispose{
            offsetValue.snapTo(0F)
            offsetValue.stop()
        }
        Row(modifier = Modifier.offset(y = offsetValue.value.toInt().dp).drawOpacity(alphaValue.value)) {
            it.forEachIndexed { rowIndex, item ->
                Box(modifier = Modifier.weight(1F).align(Alignment.Top).padding(8.dp), alignment = Alignment.Center) {
                    itemContent(item, index * rows + rowIndex)
                }
            }
            repeat(rows - it.size) {
                Box(modifier = Modifier.weight(1F).padding(8.dp)) {}
            }
        }
    }
}

@Composable
fun Dialog(state: MutableState<Boolean>, title: String, desc: String, pText: String, onClick: () -> Unit) {
    if (state.value) {
        val bgColor = MaterialTheme.colors.surface
        AlertDialog(title = { H6(text = title) }, text = { Body2(text = desc) }, buttons = {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.End) {
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                    TextButton(text = "CANCEL", modifier = Modifier.clickable(onClick = { state.value = false }))
                }
                WidthSpacer(value = 16)
                TextButton(text = pText.toUpperCase(Locale.ENGLISH), modifier = Modifier.clickable(onClick = {
                    state.value = false
                    onClick()
                }))
            }
        }, onDismissRequest = { state.value = false }, backgroundColor = AmbientElevationOverlay.current?.apply(bgColor, 25.dp) ?: bgColor)
    }
}

@Composable
fun ColumnLine(modifier: Modifier = Modifier) {
    HeightSpacer(value = 8)
    Divider(modifier = modifier.preferredHeight((0.8).dp).fillMaxWidth(), color = MaterialTheme.colors.onSurface.copy(alpha = 0.3F))
    HeightSpacer(value = 8)
}

@Composable
fun WithState(
    contentState: State<ContentState>,
    IdleView: (@Composable () -> Unit) = { },
    errorMessage: String = "Something went wrong",
    emptyMessage: String = "",
    content: @Composable () -> Unit
) {
    when (contentState.value) {
        ContentState.LOADING -> {
            LoadingView()
        }
        ContentState.ERROR -> {
            ErrorView(errorMessage)
        }
        ContentState.DATA -> {
            content()
        }
        ContentState.EMPTY -> {
            EmptyView(emptyMessage)
        }
        ContentState.IDLE -> {
            IdleView.invoke()
        }
    }
}

@Composable
fun ToolBar(title: String, onBackClick: () -> Unit) {
    val bgColor = MaterialTheme.colors.surface
    TopAppBar(title = { H6(text = title, modifier = Modifier.offset(x = (-20).dp)) },
            navigationIcon = {
                Image(asset = vectorResource(id = R.drawable.ic_baseline_arrow_back_24), colorFilter = OnSurfaceTint(), modifier = Modifier.offset(x = 12.dp).clickable { onBackClick() })
            }, backgroundColor = AmbientElevationOverlay.current?.apply(bgColor, 4.dp) ?: bgColor)
}
