package com.example.thcaptorsapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Layout minimal
        setContentView(R.layout.activity_main)

        // Rediriger vers l'écran de login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
