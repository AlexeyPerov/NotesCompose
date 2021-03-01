package com.casualapps.platform.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.openURL(url: String) = startActivity(Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse(url)
})