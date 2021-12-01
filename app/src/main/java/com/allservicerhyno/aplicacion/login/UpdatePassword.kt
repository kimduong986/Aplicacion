package com.allservicerhyno.aplicacion.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allservicerhyno.aplicacion.databinding.UpdatePasswordBinding


class UpdatePassword : AppCompatActivity() {
    
    lateinit var binding: UpdatePasswordBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =UpdatePasswordBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        
        
        
    }
}