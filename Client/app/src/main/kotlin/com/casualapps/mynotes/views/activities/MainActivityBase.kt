package com.casualapps.mynotes.views.activities

import com.casualapps.mynotes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityBase : ActivityBase() {
    override val layoutId: Int
        get() = R.layout.activity_main
}
