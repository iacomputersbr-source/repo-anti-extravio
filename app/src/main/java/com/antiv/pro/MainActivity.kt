package com.example.antiextravio

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = TextView(this)
        t.text = "ANTI EXTRAVIO - FIX FINAL"
        t.textSize = 28f
        t.setPadding(50, 400, 50, 50)
        setContentView(t)
    }
}
