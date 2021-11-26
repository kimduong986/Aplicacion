package com.allservicerhyno.aplicacion.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.allservicerhyno.aplicacion.authenticate.Authentication
import com.allservicerhyno.aplicacion.authenticate.AuthenticationData
import com.allservicerhyno.aplicacion.authenticate.POSTAuthenticate
import com.allservicerhyno.aplicacion.R
import com.allservicerhyno.aplicacion.dashboard.Main
import com.allservicerhyno.aplicacion.room.NetworkHelper
import com.allservicerhyno.aplicacion.room.User
import com.allservicerhyno.aplicacion.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    private lateinit var database : UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        //Notification
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channelId).also {
            it.setContentTitle("Notificacion")
            it.setContentText("Bienvenido")
            it.setSmallIcon(R.drawable.cuenta)
            it.priority = NotificationCompat.PRIORITY_HIGH
        }.build()
        val notificationManager = NotificationManagerCompat.from(this)

        //Login.xml
        val user = findViewById<EditText>(R.id.Email)
        val pass = findViewById<EditText>(R.id.Password)
        val btnLogin = findViewById<Button>(R.id.button)
        //Check connection with Allser Service
        btnLogin.setOnClickListener {
           if (NetworkHelper.isNetworkConnection(this@Login)) {
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
                       pass.text.toString()
                   )
               )
               val repos = service.authenticate(auth)
               login(repos, retrofit)
           }
           else {

                if (user.text.isNotEmpty() && pass.text.isNotEmpty())
                {
                    notificationManager.notify(notificationId, notification)

                    val userInfo = User(0, user.text.toString(), pass.text.toString() )
                        UserDatabase.getInstance(this@Login).userDao().insert(userInfo)
                    Toast.makeText(this@Login, "Registro Exitoso", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@Login, "Error", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    //Notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance  = NotificationManager.IMPORTANCE_HIGH

            val channel  = NotificationChannel(channelId, channelName, importance).apply {
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