package com.casualapps.mynotes.views.fragments

import androidx.compose.animation.transition
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.casualapps.platform.extensions.matchParent
import com.casualapps.mynotes.compose.animaton.LoginEntryTransitionState
import com.casualapps.mynotes.compose.animaton.alphaPropKey
import com.casualapps.mynotes.compose.animaton.mainFragmentTransition
import com.casualapps.mynotes.compose.layout.*
import com.casualapps.platform.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import com.casualapps.mynotes.R.drawable

@AndroidEntryPoint
class LoginEntryFragment : FragmentBase() {
    private var _animation = true

    @Composable
    override fun setContent() {
        Box(modifier = Modifier.matchParent(), alignment = Alignment.Center) {
            val mainTransitionState = transition(definition = mainFragmentTransition,
                initState = if (_animation) LoginEntryTransitionState.START else LoginEntryTransitionState.END, toState = LoginEntryTransitionState.END)
            _animation = false

            val alpha = mainTransitionState[alphaPropKey]
            LoginOptions(alpha)
        }
    }

    @Composable
    fun LoginOptions(alpha: Float) {
        Column(
            Modifier
                .padding(start = 16.dp, end = 16.dp)
                .drawOpacity(alpha),
            verticalArrangement = Arrangement.Center
            )
        {
            HeightSpacer(value = 2)
            CardButton(
                text = "Sign In",
                icon = drawable.ic_baseline_login_24,
                onClick = {
                    navController.navigateSafely(LoginEntryFragmentDirections.actionLoginEntryFragmentToSignInFragment())
                })
            HeightSpacer(value = 10)
            CardButton(
                text = "Sign Up",
                icon = drawable.ic_baseline_account_circle_24,
                onClick = {
                    navController.navigateSafely(LoginEntryFragmentDirections.actionLoginEntryFragmentToSignUpFragment())
                })
            HeightSpacer(value = 10)
            CardButton(
                text = "Guest",
                icon = drawable.ic_baseline_arrow_forward_24,
                onClick = {
                    navController.navigateSafely(LoginEntryFragmentDirections.actionLoginEntryFragmentToInitializationFragment().setUserId(0L))
                })
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LoginOptions(1f)
    }
}