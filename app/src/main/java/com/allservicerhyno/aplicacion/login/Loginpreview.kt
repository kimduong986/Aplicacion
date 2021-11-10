package com.allservicerhyno.aplicacion.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.allservicerhyno.aplicacion.R

class Loginpreview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpreview)

        //Codigo para ingresar a la siguiente pagina despues de
        // darle al boton de ingresar en el menu de inicio
        val boton1 =findViewById<Button>(R.id.btnIniciarSesion)
        boton1.setOnClickListener{
            val lanzar = Intent(this, Login1::class.java)
            startActivity(lanzar)
        }
    }
}