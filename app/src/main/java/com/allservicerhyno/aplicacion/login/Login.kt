package com.allservicerhyno.aplicacion.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.allservicerhyno.aplicacion.R
import com.allservicerhyno.aplicacion.authenticate.Authentication
import com.allservicerhyno.aplicacion.authenticate.AuthenticationData
import com.allservicerhyno.aplicacion.authenticate.POSTAuthenticate
import com.allservicerhyno.aplicacion.dashboard.Main
import com.allservicerhyno.aplicacion.room.User
import com.allservicerhyno.aplicacion.room.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    //Notification
    private val channelId = "channelId"
    private val channelName = "channelName"
    private var notificationId = 0

    //Room Database
    private lateinit var  mUserViewModel: UserViewModel

 

    //Init Login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        //Login.xml
        val user = findViewById<EditText>(R.id.Email)
        val pass = findViewById<EditText>(R.id.Password)
        val btnLogin = findViewById<Button>(R.id.button)
        //Notification
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channelId).also {
            it.setContentTitle("Notificacion")
            it.setContentText("Bienvenido")
            it.setSmallIcon(R.drawable.cuenta)
            it.priority = NotificationCompat.PRIORITY_HIGH
        }.build()
        val notificationManager = NotificationManagerCompat.from(this)

        //Room Database
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        //Check connection with Allser Service
        btnLogin.setOnClickListener {
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
                        "odoo14", user.text.toString(),
                        pass.text.toString()))
                val repos = service.authenticate(auth)
                login(repos, retrofit)
            } else {
                insertDataToDatabase()
                notificationManager.notify(notificationId, notification)
                val lanzar = Intent(this, Main::class.java)
                startActivity(lanzar)
            }

        }

    }

    private fun insertDataToDatabase() {
    
        val user = findViewById<EditText>(R.id.Email)
        val pass = findViewById<EditText>(R.id.Password)
        
        val login = user.text.toString()
        val password = pass.text.toString()

        if (inputCheck(login, password)){
            val users =User (1, login, password)
            mUserViewModel.addUser(users)
            Toast.makeText(this,"Base de Datos Creada!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(login: String, password : String):Boolean{
        return !(TextUtils.isEmpty(login) && TextUtils.isEmpty(password))
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))

    }

    //Notification fun
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    //If failed Connection with Login
    private fun login(repos: Call<AuthenticationData?>?, retrofit: Retrofit) {
        repos?.enqueue(object : Callback<AuthenticationData?> {
            override fun onResponse(
                call: Call<AuthenticationData?>,
                response: Response<AuthenticationData?>
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