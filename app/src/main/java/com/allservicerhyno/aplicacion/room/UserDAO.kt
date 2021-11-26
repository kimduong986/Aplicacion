package com.allservicerhyno.aplicacion.room

import androidx.room.*


@Dao
interface UserDAO {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(user: User?)
    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getAllUserInfo(): List<User>?
    @Delete
    fun delete(user: User?)
    @Update
    fun update(user: User?)
}