package com.allservicerhyno.aplicacion.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite (context: Context): SQLiteOpenHelper(
    context, "SQLite.db", null, 1){

    /**
     * Nuestro método onCreate ().
     * Se llama cuando se crea la base de datos por
     * primera vez.Aquí es donde debería ocurrir
     * la creación de tablas y la población inicial
     * de las tablas.
     */

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE usuario" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "password TEXT, email TEXT)"
        db!!.execSQL(ordenCreacion)
    }

    /**
     * Vamos a crear nuestro método onUpgrade ()
     * Llamado cuando la base de datos necesita ser actualizada.
     * La implementación debe utilizar este método para
     * eliminar tablas,agregar tablas o hacer cualquier otra cosa
     * que necesite para actualizar a la nueva versión del esquema.
     */

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
       val ordenBorrado = "DROP TABLE IF EXISTS usuario"
        db!!.execSQL(ordenBorrado)
    }

    /**
     *Creemos nuestro método insertData ().
     *Insertará datos en la base de datos SQLIte.
     */

    fun insertarDato(email: String, password: String ){
        val datos = ContentValues()
        datos.put("email", email)
        datos.put("password", password)

        val db = this.writableDatabase
        db.insert("usuario", null, datos)
        db.close()
    }

    /**
     * Creemos un método para actualizar una fila con nuevos valores de campo.
     */
    fun updateData(id: Int, email: String, password: String) {
        val args = arrayOf(id.toString())

        val datos = ContentValues()
        datos.put("password", password)

        val db = this.writableDatabase
        db.update("usuario", datos, "email = $email", args)
        db.close()
    }
}

