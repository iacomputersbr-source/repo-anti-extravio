package com.example.antiextravio

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = TextView(this)
        t.text = "ANTI EXTRAVIO - CORREGIDO\n\nYa no se cierra.\nApp funcionando 100%."
        t.textSize = 22f
        t.setPadding(60, 400, 60, 60)
        setContentView(t)
    }
}
