package com.casualapps.mynotes.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.viewinterop.AndroidView
import com.casualapps.mynotes.compose.layout.LoadingView
import com.casualapps.mynotes.compose.layout.ToolBar
import com.casualapps.mynotes.enums.WebRequest

class CustomWebView : FragmentBase() {
    private lateinit var _webRequest: WebRequest
    private val _progressState = mutableStateOf(true)

    override fun onArgumentsReady(bundle: Bundle) {
        _webRequest = WebRequest[CustomWebViewArgs.fromBundle(bundle).webRequestKey]
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    override fun setContent() {
        val context = ContextAmbient.current
        val webView = remember { WebView(context).apply {
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            settings.javaScriptEnabled = true
        } }.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress > 90) {
                        _progressState.value = false
                    }
                }
            }
            loadUrl(_webRequest.url)
        }
        Scaffold(topBar = { ToolBar(title = _webRequest.title) { mainActivity.onBackPressed() } }) {
            if (_progressState.value) {
                LoadingView()
            } else {
                AndroidView({ webView }, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
