package com.allservicerhyno.aplicacion.login

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.allservicerhyno.aplicacion.authenticate.Authentication
import com.allservicerhyno.aplicacion.authenticate.AuthenticationData
import com.allservicerhyno.aplicacion.authenticate.POSTAuthenticate
import com.allservicerhyno.aplicacion.dashboard.Main
import com.allservicerhyno.aplicacion.databinding.LoginBinding
import com.allservicerhyno.aplicacion.room.App
import com.allservicerhyno.aplicacion.room.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    
    
    //Init Login
    lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        
        //Check connection with Allser Service
        binding.button.setOnClickListener {
            if (isNetworkAvailable()) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val userInfo = Persona(
                            0,
                            binding.Email.text.toString(), binding.Password.text.toString()
                        )
                        App.getDb().personaDao().insert(userInfo)
                    }
                }
                //Connection with All Service
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
                        //When you change the name of the database,
                        // it should also be changed here, because the data you collect
                        // comes from the database that the server currently has.
                        "odoo14", binding.Email.text.toString(),
                        binding.Password.text.toString()
                    )
                )
                val repos = service.authenticate(auth)
                login(repos, retrofit)
            } else {
                if (binding.Email.text.isEmpty() && binding.Password.text.isEmpty()) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            App.getDb().personaDao().getUser(binding.Email.text.toString(), binding.Password.text.toString())
                        }
                    }
                    val lanzar = Intent(this, Main::class.java)
                    startActivity(lanzar)
                    
                }else{
                    Toast.makeText(this@Login, "Ingresar Correo / Contraseña", Toast.LENGTH_SHORT).show()
                }
                
            }
            
        }
        
        //Restore password
        binding.restablecer.setOnClickListener {
            val lanzar = Intent(this, UpdatePassword::class.java)
            startActivity(lanzar)
        }
        
    }
    
    //Check connection with Allser Service
    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        
        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))
        
    }
    
    //If failed Connection with Login
    private fun login(repos: Call<AuthenticationData?>?, retrofit: Retrofit) {
        repos?.enqueue(object : Callback<AuthenticationData?> {
            override fun onResponse(
                call: Call<AuthenticationData?>,
                response: Response<AuthenticationData?>,
            ) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    if (userData!!.result != null) {
                        val intent = Intent(this@Login, Main::class.java)
                        startActivity(intent)
                        Toast.makeText(this@Login, "Bienvenido!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@Login,
                            "Usuario/Contraseña Incorrectas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@Login,
                        "Usuario/Contraseña Incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            
            override fun onFailure(call: Call<AuthenticationData?>, t: Throwable) {
                Toast.makeText(
                    this@Login,
                    "Error de autentication. Intentelo de nuevo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}