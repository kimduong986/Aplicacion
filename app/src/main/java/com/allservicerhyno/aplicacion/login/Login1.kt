package com.allservicerhyno.aplicacion.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.allservicerhyno.aplicacion.authenticate.Authentication
import com.allservicerhyno.aplicacion.authenticate.AuthenticationData
import com.allservicerhyno.aplicacion.authenticate.POSTAuthenticate
import com.allservicerhyno.aplicacion.R
import com.allservicerhyno.aplicacion.dashboard.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val user = findViewById<EditText>(R.id.Email)
        val pass = findViewById<EditText>(R.id.Password)
        val btnLogin = findViewById<Button>(R.id.button)
        btnLogin.setOnClickListener {
            if (user.length() == 0) {
                user.error = "Ingresar su correo "
            }
            if (pass.length() == 0) {
                pass.error = "Ingresar su contraseña "
            }
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.allser.com.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(
                POSTAuthenticate::class.java
            )
            val auth = Authentication(
                "2.0",
                Authentication.Params(
                    "odoo", user.text.toString(),
                    pass.text.toString()
                )
            )
            val repos = service.authenticate(auth)
            login(repos, retrofit)
        }
    }

    private fun login(repos: Call<AuthenticationData>, retrofit: Retrofit) {
        repos.enqueue(object : Callback<AuthenticationData?> {
            override fun onResponse(
                call: Call<AuthenticationData?>,
                response: Response<AuthenticationData?>
            ) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    if (userData!!.result != null) {
                        val intent = Intent(this@Login1, Main::class.java)
                        startActivity(intent)
                        Toast.makeText(this@Login1, "Bienvenido!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@Login1,
                            "Usuario/Contraseña Incorrectas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@Login1,
                        "Usuario/Contraseña Incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AuthenticationData?>, t: Throwable) {
                Toast.makeText(
                    this@Login1,
                    "Error de autentication. Intentelo de nuevo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}