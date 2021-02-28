package com.casualapps.mynotes.views.fragments

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.casualapps.mynotes.data.entities.Note
import com.casualapps.mynotes.data.entities.Category
import com.casualapps.mynotes.data.entities.NoteArguments
import com.casualapps.platform.extensions.textToast
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.layout.*
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.CategoryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : FragmentBase() {
    private lateinit var category: Category

    private val viewModel: CategoryDetailViewModel by viewModels()
    private val animateState = mutableStateOf(2)

    override fun onArgumentsReady(bundle: Bundle) {
        category = CategoryFragmentArgs.fromBundle(bundle).category
    }

    override fun onStop() {
        super.onStop()
        exitFullScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
    }

    override fun initViews() {
        viewModel.updateBookmarkStatus(category)
        viewModel.updateNotes(category)
        ioScope.launch {
            var plus = true
            while (isActive) {
                delay(32)
                animateState.value = animateState.value + 1 * if (plus) 1 else -1
                plus = !plus
            }
        }
    }

    @Composable
    override fun setContent() {
        Column {
            CategoryDetailView(category)
            H6(text = "Notes", modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            Divider(modifier = Modifier.preferredHeight((0.8).dp).fillMaxWidth().padding(horizontal = 12.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3F))
            WithState(contentState = viewModel.contentState) {
                NotesList(viewModel.notesLiveData)
            }
        }
    }

    @Composable
    private fun NotesList(noteLiveData: LiveData<List<Note>>) {
        val notesState = noteLiveData.observeAsState(emptyList())
        LazyColumnFor(items = notesState.value) { note ->
            NoteCard(title = note.title, contents = note.contents) {
                navController.navigateSafely(
                    CategoryFragmentDirections.actionCategoryFragmentToNoteFragment(
                        NoteArguments(categoryId = category.id, note = note)
                    )
                )
            }
        }
    }

    @Composable
    fun CategoryDetailView(category: Category) {
        val surfaceGradient = backgroundGradient().reversed()
        Box {
            BookmarkButton()
            WithConstraints {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(4 / 3F)
                        .background(VerticalGradient(surfaceGradient, 0F, constraints.maxHeight.toFloat(), TileMode.Clamp))
                ) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                        CategoryInfo(category = category)
                    }
                }
            }
        }
    }

    @Composable
    fun CategoryInfo(category: Category) {
        HeightSpacer(16)
        val padding = Modifier.padding(horizontal = 16.dp)
        Text(
                text = category.name,
                style = MaterialTheme.typography.h6,
                modifier = padding
        )
        HeightSpacer(8)

        val idText = annotatedString {
            val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
                background = MaterialTheme.colors.primary.copy(alpha = 0.8f)
            )
            withStyle(tagStyle) {
                append(category.id)
            }
        }

        ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
            Text(
                text = idText,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(Modifier.preferredHeight(16.dp))
    }

    @Composable
    fun BookmarkButton() {
        val bookmarksState = viewModel.bookmarksLiveData.observeAsState(initial = false)
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Image(asset = vectorResource(
                    id = if (bookmarksState.value) R.drawable.ic_baseline_bookmark_active_24 else R.drawable.ic_baseline_bookmark_in_active_24),
                    colorFilter = ColorFilter.tint(if (bookmarksState.value) MaterialTheme.colors.primary else Color.White),
            modifier = Modifier.size(56.dp)
                    .padding(8.dp).background(Color.Black.copy(alpha = 0.6F), shape = CircleShape)
                    .align(Alignment.TopEnd).clickable {
                        if (bookmarksState.value) {
                            textToast("Removed from Bookmarks")
                            viewModel.removeFromBookmarks(category)
                        } else {
                            textToast("Added to Bookmarks")
                            viewModel.addToBookmarks(category)
                        }
                    }, contentScale = ContentScale.Inside)

            Image(asset = vectorResource(id = R.drawable.ic_baseline_arrow_back_24),
                    modifier = Modifier.size(56.dp)
                            .padding(8.dp).background(Color.Black.copy(alpha = 0.6F), shape = CircleShape)
                            .align(Alignment.TopStart).clickable {
                                mainActivity.onBackPressed()
                            }, contentScale = ContentScale.Inside)
        }
    }

    @Composable
    fun backgroundGradient(): List<Color> {
        return listOf(
                MaterialTheme.colors.surface,
                MaterialTheme.colors.surface.copy(alpha = 0.9F),
                MaterialTheme.colors.surface.copy(alpha = 0.8F),
                MaterialTheme.colors.surface.copy(alpha = 0.7F),
                Color.Transparent)
    }
}
