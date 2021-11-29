package com.allservicerhyno.aplicacion.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun addUser(user: User)

    @Query("SELECT * FROM User_table ORDER BY id DESC")
    fun  readAllData():LiveData<List<User>>
}