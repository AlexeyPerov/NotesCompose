package com.casualapps.mynotes.views.fragments

import android.os.Bundle
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import com.casualapps.mynotes.compose.layout.*
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.CategoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CategoriesGridFragment : FragmentBase() {
    private val _viewModel: CategoriesListViewModel by viewModels()

    override fun onArgumentsReady(bundle: Bundle) {
    }

    override fun initViews() {
        _viewModel.fetchCategories()
    }

    @Composable
    override fun setContent() {
        Scaffold(topBar = {
            ToolBar(title = "Notes") {
                mainActivity.onBackPressed()
            }
        }) {
            val categoriesStateFlow = _viewModel.categoriesStateFlow.collectAsState(
                initial = emptyList()
            )
            WithState(contentState = _viewModel.state) {
                LazyGridFor(categoriesStateFlow.value) { category, _ ->
                    CategoryGridCard(category = category) {
                        navController.navigateSafely(
                            CategoriesGridFragmentDirections.actionCategoriesGridFragmentToCategoryFragment(
                                category
                            )
                        )
                    }
                }
            }
        }
    }
}
