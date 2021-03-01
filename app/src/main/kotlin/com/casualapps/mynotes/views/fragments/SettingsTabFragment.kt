package com.casualapps.mynotes.views.fragments

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.casualapps.platform.extensions.openURL
import com.casualapps.mynotes.R
import com.casualapps.mynotes.compose.layout.CardButton
import com.casualapps.mynotes.compose.layout.ColumnLine
import com.casualapps.mynotes.compose.layout.Dialog
import com.casualapps.mynotes.compose.layout.Link
import com.casualapps.mynotes.compose.layout.Toast
import com.casualapps.mynotes.compose.layout.HeightSpacer
import com.casualapps.mynotes.enums.WebRequest
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsTabFragment : FragmentBase() {
    private val _viewModel: SettingsViewModel by viewModels()
    private val _mainNavigationController by lazy { Navigation.findNavController(requireActivity(),
        R.id.app_host_fragment) }
    private val _dialogState = mutableStateOf(false)

    override fun initObservers() {
        _viewModel.logoutLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                _mainNavigationController.navigateSafely(MainFragmentDirections.actionMainFragmentToLoginEntryFragmentReplace())
            }
        }
    }

    @Composable
    override fun setContent() {
        val adminPanelRequest = WebRequest.ADMIN_PANEL
        val sourceRequest = WebRequest.SOURCE_CODE

        Scaffold(modifier = Modifier.padding(16.dp)) {
            Column(Modifier.fillMaxWidth()) {
                UserCard()
                HeightSpacer(value = 16)

                CardButton(text = adminPanelRequest.title, onClick = {
                    _mainNavigationController.navigateSafely(MainFragmentDirections
                        .actionMainFragmentToWebView()
                        .setWebRequestKey(adminPanelRequest.key))
                })
                HeightSpacer(value = 16)
                ColumnLine()
                Link(text = sourceRequest.title, modifier = Modifier.clickable {
                    mainActivity.openURL(sourceRequest.url)
                })
                ColumnLine()
            }
        }
        Dialog(state = _dialogState, title = "Logout", desc = "Are you sure you want to Logout?", pText = "Confirm") {
            _viewModel.logout()
        }
    }

    override fun initViews() {
        _viewModel.fetchUser()
    }

    @Composable
    fun UserCard() {
        val userNameState = _viewModel.userNameLiveData.observeAsState()
        Card(
            Modifier
                .fillMaxWidth()
                .height(60.dp), elevation = 4.dp, shape = MaterialTheme.shapes.medium) {
            Box(modifier = Modifier.padding(16.dp, 0.dp)) {
                Text(
                    text = "${userNameState.value ?: ""}",
                    modifier = Modifier.align(
                        Alignment.CenterStart
                    ),
                    style = MaterialTheme.typography.h6
                )
                LoginStateButton()
            }
        }
        if (_viewModel.logoutLiveData.observeAsState().value == true) {
            Toast(message = "Logged Out Successfully!")
        }
    }

    @Composable
    fun BoxScope.LoginStateButton() {
        CardButton(
                text = if (_viewModel.loginState.value) "Logout" else "Login",
                modifier = Modifier
                    .width(80.dp)
                    .align(Alignment.CenterEnd), height = 30) {
            if (_viewModel.loginState.value) {
                _dialogState.value = true
            } else {
                _mainNavigationController.navigateSafely(MainFragmentDirections.actionMainFragmentToLoginEntryFragmentReplace())
            }
        }
    }
}
