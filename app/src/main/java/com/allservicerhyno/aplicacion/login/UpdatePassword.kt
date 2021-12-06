package com.allservicerhyno.aplicacion.login

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.allservicerhyno.aplicacion.databinding.UpdatePasswordBinding
import com.allservicerhyno.aplicacion.room.App
import com.allservicerhyno.aplicacion.room.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.R.attr.password





class UpdatePassword : AppCompatActivity() {
    
    private lateinit var binding: UpdatePasswordBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdatePasswordBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        
        //Restore password
        binding.actualizar.setOnClickListener {
            
            if (binding.Email.text.toString().isEmpty()) {
                Toast.makeText(this@UpdatePassword, "Ingresar su Correo Electronico", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.Password.text.toString().isEmpty()){
                    Toast.makeText(this@UpdatePassword, "Ingresar una Contraseña", Toast.LENGTH_SHORT).show()
                }else{
                    val  user: Boolean? =  null
                    val updateUser = Persona(null, binding.Email.text.toString(), binding.Password.text.toString())
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            App.getDb().personaDao().update(updateUser)
                        }
                    }
                    if (user == true){
                        val lanzar = Intent(this@UpdatePassword, Login::class.java)
                        startActivity(lanzar)
                    }else{
                        Toast.makeText(this@UpdatePassword, "Error de Usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
