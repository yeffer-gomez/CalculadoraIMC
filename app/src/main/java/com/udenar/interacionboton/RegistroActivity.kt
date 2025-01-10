package com.udenar.interacionboton

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

/**
 *  muestra un listado de registros almacenados en la base de datos SQLite.
 * Los registros se muestran en un ListView.
 */
class RegistroActivity : AppCompatActivity() {

    // Declaración de variables para interactuar con la base de datos,
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var btnSalir: AppCompatButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Asignación de vistas a las variables
        listView = findViewById(R.id.listViewRegistros) // ListView para mostrar los registros
        btnSalir = findViewById(R.id.btnSalir) //
        databaseHelper = DatabaseHelper(this) // Instancia del helper de la base de datos

        // Obtener los registros desde la base de datos
        val registros = databaseHelper.obtenerRegistros()

        // Crear un ArrayAdapter para mostrar los registros en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, registros)
        listView.adapter = adapter


        btnSalir.setOnClickListener {
            finish()
        }
    }
}
