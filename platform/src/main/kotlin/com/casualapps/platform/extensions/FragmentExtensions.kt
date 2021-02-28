package com.casualapps.platform.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.textToast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
