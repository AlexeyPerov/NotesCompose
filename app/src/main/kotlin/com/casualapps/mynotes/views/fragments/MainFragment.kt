package com.casualapps.mynotes.views.fragments

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.casualapps.platform.extensions.matchParent
import com.casualapps.mynotes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : FragmentBase() {
    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun initViews() {
        view<ComposeView>(R.id.titleComposeView)?.setContent {
            Column(modifier = Modifier.matchParent(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Notes", Modifier.size(40.dp))
            }
        }
        val controller = (childFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment).navController
        view<BottomNavigationView>(R.id.mainBottomNav)?.setupWithNavController(controller)
    }
}
