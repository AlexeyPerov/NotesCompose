package com.casualapps.mynotes.views.fragments

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.casualapps.platform.extensions.matchParent
import com.casualapps.platform.extensions.textToast
import com.casualapps.mynotes.compose.layout.CardButton
import com.casualapps.mynotes.compose.layout.H6
import com.casualapps.mynotes.compose.layout.HeightSpacer
import com.casualapps.platform.utils.navigateSafely
import com.casualapps.mynotes.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : FragmentBase() {
    private val viewModel: LoginViewModel by viewModels()

    @Composable
    override fun setContent() {
        Box(Modifier.matchParent()) {
            Column(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    HeightSpacer(value = 30)
                    H6(text = "Sign In")
                    HeightSpacer(value = 30)
                }
                val userNameErrorState = viewModel.userNameErrorLiveData.observeAsState()
                val passwordErrorState = viewModel.passwordErrorLiveData.observeAsState()
                val username = remember { mutableStateOf(TextFieldValue("user")) }

                OutlinedTextField(value = username.value,
                    onValueChange = { username.value = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    isErrorValue = userNameErrorState.value != null,
                    errorColor = MaterialTheme.colors.error)
                Text(
                    text = userNameErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = TextUnit(12),
                    modifier = Modifier.height(userNameErrorState.value?.let { 20.dp } ?: 0.dp),
                    color = MaterialTheme.colors.error)
                HeightSpacer(value = 16)

                val password = remember { mutableStateOf(TextFieldValue("pass")) }
                OutlinedTextField(value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isErrorValue = passwordErrorState.value != null,
                    errorColor = MaterialTheme.colors.error
                )
                Text(
                    text = passwordErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = TextUnit(12),
                    modifier = Modifier.height(passwordErrorState.value?.let { 20.dp } ?: 0.dp),
                    color = MaterialTheme.colors.error)
                HeightSpacer(value = 30)

                CardButton(text = "Login", onClick = {
                    viewModel.login(username = username.value.text, password = password.value.text)
                })
            }
        }
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            if (it != -1L) {
                navController.navigateSafely(SignInFragmentDirections.actionSignInFragmentToInitializationFragment().setUserId(it))
            } else {
                textToast("Login Error")
            }
        }
    }
}
