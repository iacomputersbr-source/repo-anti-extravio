package com.antiv.pro

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 96, 48, 48)
        }

        val titleLayout = TextInputLayout(this).apply {
            hint = "Numero de emergencia"
            boxStrokeColor = getColor(android.R.color.holo_green_light)
        }
        val inputEmergencia = TextInputEditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_PHONE
        }
        titleLayout.addView(inputEmergencia)

        val claveLayout = TextInputLayout(this).apply {
            hint = "Palabra clave SMS"
        }
        val inputClave = TextInputEditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_TEXT
        }
        claveLayout.addView(inputClave)

        val licenciaLayout = TextInputLayout(this).apply {
            hint = "Codigo licencia XXXXX-XXXXX-XXXXX-XXXXX-XXXXX"
        }
        val inputLicencia = TextInputEditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_TEXT
            isAllCaps = true
        }
        licenciaLayout.addView(inputLicencia)

        val btnActivar = MaterialButton(this).apply {
            text = "Activar"
            setOnClickListener {
                val num = inputEmergencia.text.toString().trim()
                val palabra = inputClave.text.toString().trim()
                val lic = inputLicencia.text.toString().trim().uppercase()

                if (num.isEmpty() || palabra.isEmpty() || lic.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val regex = Regex("^[A-Z0-9]{5}(-[A-Z0-9]{5}){4}$")
                if (!regex.matches(lic)) {
                    Toast.makeText(this@MainActivity, "Licencia invalida. Formato: XXXXX-XXXXX-XXXXX-XXXXX-XXXXX", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // Guardado seguro con EncryptedSharedPreferences (ejemplo)
                // val masterKey = MasterKey.Builder(this@MainActivity).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
                // val prefs = EncryptedSharedPreferences.create(...)

                Toast.makeText(this@MainActivity, "ANTI-EXTRAVIO ACTIVADO PRO", Toast.LENGTH_LONG).show()
            }
        }

        container.addView(titleLayout)
        container.addView(claveLayout)
        container.addView(licenciaLayout)
        container.addView(btnActivar)

        setContentView(container)
    }
}
