package com.example.antiextravio

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var admin: ComponentName
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        admin = ComponentName(this, AdminReceiver::class.java)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 120, 40, 40)

        val titulo = TextView(this)
        titulo.text = "ANTI EXTRAVIO\n"
        titulo.textSize = 24f
        layout.addView(titulo)

        status = TextView(this)
        status.textSize = 16f
        layout.addView(status)

        val btnAdmin = Button(this)
        btnAdmin.text = "1. ACTIVAR PROTECCION"
        layout.addView(btnAdmin)

        val btnBloquear = Button(this)
        btnBloquear.text = "2. BLOQUEAR AHORA"
        layout.addView(btnBloquear)

        val btnAlarma = Button(this)
        btnAlarma.text = "3. PROBAR ALARMA"
        layout.addView(btnAlarma)

        setContentView(layout)
        actualizar()

        btnAdmin.setOnClickListener {
            val i = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
            i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Activa para proteger contra extravio")
            startActivityForResult(i, 1)
        }

        btnBloquear.setOnClickListener {
            if (dpm.isAdminActive(admin)) {
                dpm.lockNow()
            } else {
                Toast.makeText(this, "Primero activa proteccion", Toast.LENGTH_LONG).show()
            }
        }

        btnAlarma.setOnClickListener {
            Toast.makeText(this, "Alarma anti-robo activa!", Toast.LENGTH_LONG).show()
        }
    }

    fun actualizar() {
        if (dpm.isAdminActive(admin)) {
            status.text = "Estado: PROTEGIDO"
        } else {
            status.text = "Estado: SIN PROTECCION"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        actualizar()
    }
}
