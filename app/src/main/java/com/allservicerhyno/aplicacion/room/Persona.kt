package com.allservicerhyno.aplicacion.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona(
   @PrimaryKey(autoGenerate = true)
    val id: Long= 0,
    val email: String,
    val password: String
)