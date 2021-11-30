package com.allservicerhyno.aplicacion.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long= 0,
    @ColumnInfo(name = "Login")
    val email: String,
    @ColumnInfo(name = "Password")
    val password: String
)
{
    override fun toString(): String {
        return "Persona(id=$id, email='$email', password='$password')\n"
    }
}