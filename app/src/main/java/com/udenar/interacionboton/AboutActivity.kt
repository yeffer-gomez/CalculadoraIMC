package com.udenar.interacionboton

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Referencia al botón de "Salir"
        val btnSalir = findViewById<AppCompatButton>(R.id.btnSalir)

        // Acción del botón para volver a MainActivity
        btnSalir.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a MainActivity
        }
    }
}
