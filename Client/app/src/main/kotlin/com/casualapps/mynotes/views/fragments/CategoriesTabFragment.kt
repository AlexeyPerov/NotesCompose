package com.casualapps.mynotes.views.fragments

import androidx.compose.foundation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.casualapps.mynotes.data.entities.NoteArguments
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.layout.*
import com.casualapps.mynotes.enums.ContentState
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CategoriesTabFragment : FragmentBase() {
    private val _viewModel: CategoriesViewModel by viewModels()

    private val mainNavigationController by lazy { Navigation.findNavController(requireActivity(),
        R.id.app_host_fragment) }

    @Composable
    override fun setContent() {
        when (_viewModel.categoriesState.value) {
            ContentState.LOADING -> {
                LoadingView()
            }
            ContentState.ERROR -> {
                ErrorView("Unable to load notes")
            }
            ContentState.DATA, ContentState.EMPTY -> {
                Column {
                    HeightSpacer(value = 8)
                    CategoriesSection()
                    SelectedCategoryNotesSection()
                }
            }
            ContentState.IDLE -> {
                EmptyView()
            }
        }
    }

    @Composable
    fun CategoriesSection() {
        val selectedCategoryId = _viewModel.selectedCategoryId.value

        val categoriesData = _viewModel.categoriesLiveData.observeAsState(initial = emptyList())
        val categories = categoriesData.value

        HeightSpacer(value = 8)
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            Text(text = "Categories", modifier = Modifier.align(Alignment.CenterStart), style = MaterialTheme.typography.h6)
            Text(text = "View all >", modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    mainNavigationController.navigateSafely(MainFragmentDirections.actionMainFragmentToCategoriesGridFragment())
                }, style = MaterialTheme.typography.overline)
        }

        val optionsIndexes = (0..categories.count()).toList()

        LazyRowFor(items = optionsIndexes) { index ->
            WidthSpacer(value = 4)
            if (index == optionsIndexes.size - 1) {
                CategoryRowAddCard() {
                    _viewModel.addCategory(it)
                }
            } else {
                val categoryIndex = optionsIndexes[index]
                val category = categories[categoryIndex]
                CategoryRowCard(category = category, isSelected = category.id == selectedCategoryId) {
                    _viewModel.selectCategory(categoryId = category.id)
                }
            }
            WidthSpacer(value = 4)
        }
    }

    @Composable
    fun SelectedCategoryNotesSection() {
        val notesData = _viewModel.notesLiveData.observeAsState(initial = emptyList())
        val notes = notesData.value
        val category = _viewModel.selectedCategoryId.value

        when (_viewModel.notesState.value) {
            ContentState.LOADING -> {
                LinearProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
            }
            ContentState.ERROR -> {
                ErrorView("Error loading notes")
            }
            ContentState.DATA, ContentState.EMPTY -> {
                val optionsIndexes = (0..notes.count()).toList()

                LazyColumnFor(items = optionsIndexes) { index ->
                    HeightSpacer(4)
                    if (index == optionsIndexes.size - 1) {
                        HeightSpacer(20)
                        NoteAddCard { title, contents ->
                            _viewModel.addNote(title, contents)
                        }
                        HeightSpacer(10)
                    } else {
                        val note = notes[index]
                        NoteCard(title = note.title, contents = note.contents) {
                            mainNavigationController.navigateSafely(MainFragmentDirections.actionMainFragmentToNoteFragment(
                                    NoteArguments(categoryId = category, note = note)
                            ))
                        }
                    }
                    HeightSpacer(4)
                }
            }
            ContentState.IDLE -> {
                EmptyView("")
            }
        }
    }
}
