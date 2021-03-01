package com.casualapps.mynotes.views.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.casualapps.platform.extensions.matchParent
import com.casualapps.mynotes.data.repo.auth.AuthStateRepository
import com.casualapps.platform.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashFragment : FragmentBase() {
    @Inject
    lateinit var authStateRepository: AuthStateRepository

    @Composable
    override fun setContent() {
        Column(modifier = Modifier
                .matchParent()
                .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            LinearProgressIndicator()
        }

        lifecycleScope.launch {
            delay(1000)
            val savedUserId = withContext(Dispatchers.IO) { authStateRepository.getLoggedInUser() }
            if (savedUserId != -1L) {
                navController.navigateSafely(
                    SplashFragmentDirections.actionSplashFragmentToInitializationFragment()
                        .setUserId(savedUserId)
                )
            } else {
                navController.navigateSafely(SplashFragmentDirections.actionSplashFragmentToLoginEntryFragment())
            }
        }
    }
}
