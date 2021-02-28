package com.casualapps.mynotes.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.casualapps.mynotes.ui.MainTheme
import com.casualapps.mynotes.views.activities.MainActivityBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class FragmentBase : Fragment() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    protected val ioScope = CoroutineScope(Dispatchers.IO)

    @Composable
    open fun setContent() {}

    open val layoutId: Int = -1

    protected val mainActivity by lazy { (requireActivity() as MainActivityBase) }

    protected val navController by lazy { Navigation.findNavController(requireView()) }

    protected fun fullScreen() {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    protected fun exitFullScreen() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            onArgumentsReady(it)
        }
        return if (layoutId == -1) {
            ComposeView(requireContext()).apply {
                setContent {
                    MainTheme {
                        Surface(color = MaterialTheme.colors.surface) {
                            Box {
                                setContent()
                            }
                        }
                    }
                }
            }
        } else {
            inflater.inflate(layoutId, container, false)
        }
    }

    protected fun <T : View> view(id: Int): T? {
        return view?.findViewById<T>(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    open fun initViews() {}
    open fun initObservers() {}
    open fun onArgumentsReady(bundle: Bundle) {}

    override fun onStop() {
        super.onStop()
        uiScope.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }
}
