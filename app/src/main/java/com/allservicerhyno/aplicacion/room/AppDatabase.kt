package com.allservicerhyno.aplicacion.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Persona::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
    
    companion object {
        private var db: AppDatabase? = null
        fun getDB(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "Login_db"
                ).build()
            }
            return db!!
            
        }
    }
}


