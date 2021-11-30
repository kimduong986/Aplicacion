package com.allservicerhyno.aplicacion.login

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.allservicerhyno.aplicacion.authenticate.Authentication
import com.allservicerhyno.aplicacion.authenticate.AuthenticationData
import com.allservicerhyno.aplicacion.authenticate.POSTAuthenticate
import com.allservicerhyno.aplicacion.dashboard.Main
import com.allservicerhyno.aplicacion.databinding.LoginBinding
import com.allservicerhyno.aplicacion.room.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    //Room Database
    private lateinit var mUserViewModel: UserViewModel
    
    
    
    //Init Login
    lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        
        //Room Database
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        
        //Check connection with Allser Service
        binding.button.setOnClickListener {
            if (isNetworkAvailable()) {
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
                        binding.Password.text.toString()))
                val repos = service.authenticate(auth)
                login(repos, retrofit)
            } else {
                insertDataToDatabase()
                val lanzar = Intent(this, Main::class.java)
                startActivity(lanzar)
            }
            
        }
        
    }
    
    private fun insertDataToDatabase() {
        
        
        val login = binding.Email.text.toString()
        val password = binding.Password.text.toString()
        
        if (inputCheck(login, password)) {
            val users = User(1, login, password)
            mUserViewModel.addUser(users)
            Toast.makeText(this, "Base de Datos Creada!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
        
    }
    
    private fun inputCheck(login: String, password: String): Boolean {
        return !(TextUtils.isEmpty(login) && TextUtils.isEmpty(password))
    }
    
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