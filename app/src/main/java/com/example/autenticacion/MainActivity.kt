package com.example.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.autenticacion.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Código del laboratorio FCM
        val intentBackgroundService = Intent(this, PushNotification::class.java)
        startService(intentBackgroundService)

        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val usuario = FirebaseAuth.getInstance().currentUser
        txtCorreo.text = usuario?.email ?: "Usuario desconocido"

        btnVolver.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}