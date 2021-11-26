package com.allservicerhyno.aplicacion.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
        @ColumnInfo(name ="Email" )val Email: String,
        @ColumnInfo(name="Password")val Password: String
        )