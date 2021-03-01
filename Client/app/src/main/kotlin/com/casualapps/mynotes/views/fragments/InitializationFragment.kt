package com.casualapps.mynotes.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.casualapps.platform.extensions.matchParent
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.InitializationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitializationFragment : FragmentBase() {
    private val _viewModel: InitializationViewModel by viewModels()
    private var _userId: Long = 0

    override fun onArgumentsReady(bundle: Bundle) {
        _userId = InitializationFragmentArgs.fromBundle(bundle).userId
    }

    @Composable
    override fun setContent() {
        Column(modifier = Modifier
                .matchParent()
                .background(MaterialTheme.colors.surface), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            LinearProgressIndicator()
        }

        lifecycleScope.launch {
            Log.d("Initialization", "Initializing repositories with userId: $_userId")
            _viewModel.initializeRepositories(_userId)
            navController.navigateSafely(InitializationFragmentDirections.actionInitializationFragmentToMainFragment())
        }
    }
}
