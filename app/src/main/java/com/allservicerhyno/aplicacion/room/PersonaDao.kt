package com.allservicerhyno.aplicacion.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonaDao {
    
    /*
    @QUERY --> SELECT
    @INSERT --> INSERTAR
    @DELETE --> ELIMINAR
    @UPDATE --> ACTUALIZAR
     */

    @Query("SELECT * FROM personas WHERE  Login = :email and Password = :password")
    fun getUser(email: String, password: String): LiveData<Persona>
    
    @Insert
    fun insert (persona: Persona)
    
    @Update
    fun update (persona: Persona)
   
    @Delete
    fun delete (persona: Persona)
    
}