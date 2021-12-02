package com.allservicerhyno.aplicacion.room

import androidx.room.*

@Dao
interface PersonaDao {
    
    /*
    @QUERY --> SELECT
    @INSERT --> INSERTAR
    @DELETE --> ELIMINAR
    @UPDATE --> ACTUALIZAR
     */
    
    @Query("SELECT * FROM personas")
    fun  findAll():List<Persona>
    
    @Query("SELECT * FROM personas WHERE id = :idPersona")
    fun findAllById(idPersona:Long):Persona
    
    @Query("SELECT * FROM personas WHERE  Login = :email and Password = :password")
    fun getUser(email: String, password: String)
    
    @Insert
    fun insert (persona: Persona)
    
    @Update
    fun update (persona: Persona)
   
    @Delete
    fun delete (persona: Persona)
    
}