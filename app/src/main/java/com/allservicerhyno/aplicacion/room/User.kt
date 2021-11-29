package com.allservicerhyno.aplicacion.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "Login")
    val Email: String?,
    @ColumnInfo(name = "Password")
    val Password: String?,
)
