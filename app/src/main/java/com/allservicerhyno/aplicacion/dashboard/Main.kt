package com.allservicerhyno.aplicacion.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allservicerhyno.aplicacion.databinding.MainBinding


class Main : AppCompatActivity() {

    lateinit var binding: MainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

    }
}