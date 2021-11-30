package com.allservicerhyno.aplicacion.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allservicerhyno.aplicacion.databinding.LoginpreviewBinding

class Loginpreview : AppCompatActivity() {
    
    
    lateinit var binding: LoginpreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginpreviewBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        //Codigo para ingresar a la siguiente pagina despues de
        // darle al boton de ingresar en el menu de inicio
        binding.btnIniciarSesion.setOnClickListener{
            val lanzar = Intent(this, Login::class.java)
            startActivity(lanzar)


        }
    }
}