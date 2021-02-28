package com.casualapps.mynotes.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ActivityBase : AppCompatActivity() {
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}
