package com.udenar.interacionboton

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//DatabaseHelper es una clase auxiliar que se utliza para gestionar las operaciones de la base de datos.
// Se utiliza para almacenar y recuperar registros relacionados con el peso, altura, fecha y hora.

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //Objeto companion que contiene constantes relacionadas con la base de datos
    companion object {
        private const val DATABASE_NAME = "imcRecords.db"
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla
        private const val TABLE_NAME = "RegistroIMC"

        // Columnas
        private const val COLUMN_ID = "id"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_ALTURA = "altura"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_HORA = "hora"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PESO REAL, " +
                "$COLUMN_ALTURA REAL, " +
                "$COLUMN_FECHA TEXT, " +
                "$COLUMN_HORA TEXT)"
        db?.execSQL(createTable) // Ejecuta la sentencia SQL para crear la tabla
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // Elimina la tabla existente si ya existe
        onCreate(db)
    }

    // Método para insertar un nuevo registro
    fun insertarRegistro(peso: Double, altura: Double, fecha: String, hora: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PESO, peso)
            put(COLUMN_ALTURA, altura)
            put(COLUMN_FECHA, fecha)
            put(COLUMN_HORA, hora)
        }
        return db.insert(TABLE_NAME, null, contentValues)// Inserta el registro y devuelve el ID
    }

    // Método para obtener los registros
    fun obtenerRegistros(): MutableList<String> {
        val db = this.readableDatabase
        val registros = mutableListOf<String>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null) // Consulta todos los registros de la tabla

        // Itera sobre los resultados del cursor y los agrega a la lista de registros
        if (cursor.moveToFirst()) {
            do {
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO))
                val altura = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ALTURA))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA))
                registros.add("Peso: $peso, Altura: $altura, Fecha: $fecha, Hora: $hora")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return registros // Devuelve la lista de registros
    }
}

