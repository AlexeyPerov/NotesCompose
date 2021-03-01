package com.casualapps.mynotes.views.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.casualapps.mynotes.data.entities.NoteArguments
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.layout.*
import com.casualapps.platform.utils.LatestData
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalLayout
@AndroidEntryPoint
class SearchTabFragment : FragmentBase() {
    private val _viewModel: SearchViewModel by viewModels()

    private val mainNavigationController by lazy { Navigation.findNavController(requireActivity(),
        R.id.app_host_fragment) }

    @Composable
    override fun setContent() {
        val searchQueryState = remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            ) {
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.body2,
                    value = searchQueryState.value,
                    onValueChange = { searchQueryState.value = it },
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RectangleShape
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                )

                Card(
                    elevation = 5.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                        .clickable {
                            _viewModel.search(searchQueryState.value)
                        }
                ) {
                    Icon(Icons.Filled.Search)
                }
            }

            HeightSpacer(value = 10)
            WithState(
                contentState = _viewModel.contentState,
                IdleView = {
                    RecentSearchList {
                        searchQueryState.value = it
                    }
                },
                emptyMessage = "Nothing found"
            ) {
                SearchResults(_viewModel)
            }
        }
    }

    @Composable
    private fun SearchResults(searchViewModel: SearchViewModel) {
        val searchFlowState =
            searchViewModel.searchShowStateFlow.collectAsState(initial = LatestData(emptyList()))
        LazyGridFor(items = searchFlowState.value.data, hPadding = 0) { note, index ->
            if (index == searchFlowState.value.data.lastIndex) {
                onActive {
                    searchViewModel.nextPage()
                }
            }
            NoteCard(note.title, note.contents) {
                mainNavigationController.navigateSafely(
                    MainFragmentDirections.actionMainFragmentToNoteFragment(
                        NoteArguments(categoryId = note.categoryId, note = note)
                    )
                )
            }
        }
    }

    @Composable
    fun RecentSearchList(onClick: (term: String) -> Unit) {
        AnimatedVisibility(
            visible = true,
            initiallyVisible = false,
            enter = fadeIn(animSpec = TweenSpec(durationMillis = 600, delay = 300))
        ) {
            Column(Modifier.fillMaxWidth()) {
                val recentSearchState =
                    _viewModel.recentSearchLiveData.observeAsState(initial = emptyList())
                if (recentSearchState.value.isNotEmpty()) {
                    HeightSpacer(value = 8)
                    IconText(text = "Recent Searches", icon = R.drawable.ic_baseline_search_24)
                }
                HeightSpacer(value = 8)
                FlowRow {
                    recentSearchState.value.forEach {
                        Chip(text = it, icon = R.drawable.ic_baseline_search_24) {
                            onClick(it)
                            _viewModel.search(it)
                        }
                    }
                }
            }
        }
    }
}