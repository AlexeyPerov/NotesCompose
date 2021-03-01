package com.casualapps.mynotes.views.fragments

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.layout.CategoryGridCard
import com.casualapps.mynotes.compose.layout.LazyGridFor
import com.casualapps.mynotes.compose.layout.WithState
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.BookmarksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksTabFragment : FragmentBase() {
    private val _viewModel: BookmarksViewModel by viewModels()

    private val mainNavigationController by lazy { Navigation.findNavController(requireActivity(),
        R.id.app_host_fragment) }

    @Composable
    override fun setContent() {
        WithState(contentState = _viewModel.contentState,
                emptyMessage = "...") {
            Bookmarks()
        }
    }

    override fun initViews() {
        _viewModel.fetchBookmarks()
    }

    @Composable
    fun Bookmarks() {
        val categoriesStateFlow = _viewModel.categoriesStateFlow.collectAsState(
            initial = emptyList()
        )

        LazyGridFor(categoriesStateFlow.value) { category, _ ->
            CategoryGridCard(category = category) {
                mainNavigationController.navigateSafely(
                    MainFragmentDirections.actionMainFragmentToCategoryFragment(
                        category
                    )
                )
            }
        }
    }
}
