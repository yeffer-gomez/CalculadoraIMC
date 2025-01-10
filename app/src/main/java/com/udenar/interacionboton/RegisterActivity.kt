package com.udenar.interacionboton

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    // Variable para manejar la autenticación de Firebase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Inicializa Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Asigna los elementos de la interfaz
        val etEmail = findViewById<EditText>(R.id.etEmail2)
        val etPassword = findViewById<EditText>(R.id.etPassword2)
        val btnRegister = findViewById<Button>(R.id.btnRegister2)
        val volver = findViewById<Button>(R.id.volver)

        // Configura el listener para el botón de registro
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Verifica que los campos no estén vacíos
            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso, muestra un mensaje y finaliza la actividad
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            // Registro fallido, muestra el mensaje de error
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                // Si algún campo está vacío, muestra un mensaje solicitando que se llenen ambos campos
                Toast.makeText(this, "Por favor, ingresa correo y contraseña", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        //listener para el botón "Volver",
        volver.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }
}
