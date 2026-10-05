package com.example.antiextravio

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = TextView(this)
        t.text = "ANTI EXTRAVIO 1.2\n\nFIX DEL ROBOT"
        t.textSize = 28f
        t.setPadding(40, 400, 40, 40)
        setContentView(t)
    }
}
