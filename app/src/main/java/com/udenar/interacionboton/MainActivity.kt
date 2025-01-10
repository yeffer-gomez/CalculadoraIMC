package com.udenar.interacionboton

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el ayudante de base de datos
        databaseHelper = DatabaseHelper(this)

        // Referencias a los elementos de la interfaz
        val btnCalcularIMC = findViewById<AppCompatButton>(R.id.btnCalcularIMC)
        val etPeso = findViewById<AppCompatEditText>(R.id.etPeso)
        val etAltura = findViewById<AppCompatEditText>(R.id.etAltura)
        val txtResultadoIMC = findViewById<TextView>(R.id.tvResultadoIMC)
        val ivCategoriaIMC = findViewById<ImageView>(R.id.ivCategoriaIMC)
        val btnAbout = findViewById<AppCompatButton>(R.id.btnAbout)
        val btnVerRegistro = findViewById<AppCompatButton>(R.id.btnVerRegistro) // Nuevo botón

        btnCalcularIMC.setOnClickListener {
            // Ocultar teclado
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            val peso = etPeso.text.toString().toDoubleOrNull() ?: 0.0
            val altura = etAltura.text.toString().toDoubleOrNull() ?: 0.0

            if (altura > 10) { // Si la altura está en cm, muestra un mensaje
                Toast.makeText(this, "Por favor, ingrese la altura en metros.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultadoIMC = if (altura > 0) peso / (altura * altura) else 0.0

            val categoria = when {
                resultadoIMC < 18.5 -> {
                    ivCategoriaIMC.setImageResource(R.drawable.bajo_peso2)
                    "Bajo peso"
                }

                resultadoIMC in 18.5..25.0 -> {
                    ivCategoriaIMC.setImageResource(R.drawable.peso_normal2)
                    "Peso normal"
                }

                resultadoIMC in 25.0..29.9 -> {
                    ivCategoriaIMC.setImageResource(R.drawable.sobrepeso2)
                    "Sobrepeso"
                }

                else -> {
                    ivCategoriaIMC.setImageResource(R.drawable.obesidad2)
                    "Obesidad"
                }
            }

            txtResultadoIMC.text =
                String.format("IMC: %.2f\nCategoría: %s", resultadoIMC, categoria)

            // Obtener la fecha y hora actual
            val sdfFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val sdfHora = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val fecha = sdfFecha.format(Date())
            val hora = sdfHora.format(Date())

            // Insertar el registro en la base de datos
            val resultado = databaseHelper.insertarRegistro(peso, altura, fecha, hora)
            if (resultado > 0) {
                Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar el registro", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción para ir a la actividad de "Acerca de"
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // Acción para ir a la actividad de "Ver Registro"
        btnVerRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}
