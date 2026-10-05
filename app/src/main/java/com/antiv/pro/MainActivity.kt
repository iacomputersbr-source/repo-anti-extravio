package com.example.antiextravio

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
        layout.setPadding(40, 100, 40, 40)

        val titulo = TextView(this)
        titulo.text = "ANTI EXTRAVIO\nProtección Activa"
        titulo.textSize = 22f
        layout.addView(titulo)

        status = TextView(this)
        status.text = "\nEstado: Verificando..."
        status.textSize = 16f
        layout.addView(status)

        val btnAdmin = Button(this)
        btnAdmin.text = "1. ACTIVAR PROTECCIÓN (Admin)"
        layout.addView(btnAdmin)

        val btnBloquear = Button(this)
        btnBloquear.text = "2. BLOQUEAR CELULAR AHORA"
        layout.addView(btnBloquear)

        val btnUbicacion = Button(this)
        btnUbicacion.text = "3. MI UBICACIÓN ACTUAL"
        layout.addView(btnUbicacion)

        val btnAlarma = Button(this)
        btnAlarma.text = "4. SONAR ALARMA ANTI-ROBO"
        layout.addView(btnAlarma)

        setContentView(layout)

        actualizarEstado()

        btnAdmin.setOnClickListener {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Activa para proteger contra extravío y poder bloquear remoto")
            startActivityForResult(intent, 1)
        }

        btnBloquear.setOnClickListener {
            if (dpm.isAdminActive(admin)) {
                dpm.lockNow()
                Toast.makeText(this, "Celular Bloqueado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Primero activa la protección", Toast.LENGTH_LONG).show()
            }
        }

        btnUbicacion.setOnClickListener {
            try {
                val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) 
                    ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (loc != null) {
                    val uri = "https://maps.google.com/?q=${loc.latitude},${loc.longitude}"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                    status.text = "\nUbicación: ${loc.latitude}, ${loc.longitude}"
                } else {
                    Toast.makeText(this, "Activa el GPS y espera 10s", Toast.LENGTH_LONG).show()
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnAlarma.setOnClickListener {
            try {
                val alarma = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                val r = RingtoneManager.getRingtone(this, alarma)
                r.play()
                Toast.makeText(this, "ALARMA SONANDO 5 SEGUNDOS", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Alarma activada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun actualizarEstado() {
        status.text = if (dpm.isAdminActive(admin)) "\nEstado: ✅ PROTEGIDO - Admin Activo" else "\nEstado: ❌ SIN PROTECCIÓN - Toca botón 1"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        actualizarEstado()
    }
}
