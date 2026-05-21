package com.example.repositoriofb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val edtEmail    = findViewById<TextInputEditText>(R.id.edtEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        val btnLogin    = findViewById<Button>(R.id.btnLogin)
        val tvError     = findViewById<TextView>(R.id.tvError)
        val progress    = findViewById<ProgressBar>(R.id.progressBar)

        // Si ya está logueado
        auth.currentUser?.let {
            navigateByRole(it.email)
            return
        }

        btnLogin.setOnClickListener {

            val email = edtEmail.text.toString().trim().lowercase()
            val pass  = edtPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                tvError.visibility = View.VISIBLE
                tvError.text = "Completa todos los campos"
                return@setOnClickListener
            }

            tvError.visibility = View.GONE
            progress.visibility = View.VISIBLE
            btnLogin.isEnabled = false

            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { result ->
                    progress.visibility = View.GONE
                    navigateByRole(result.user?.email)
                }
                .addOnFailureListener { e ->
                    progress.visibility = View.GONE
                    btnLogin.isEnabled = true
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Error: ${e.message}"
                }
        }
    }

    // 🔥 SOLO AQUÍ SE DEFINE EL ROL
    private fun navigateByRole(email: String?) {

        val safeEmail = email?.trim()?.lowercase()

        val dest = if (safeEmail == "admin@gmail.com") {
            MainActivity::class.java   // ADMIN
        } else {
            Visualizar::class.java     // ALUMNO
        }

        startActivity(Intent(this, dest))
        finish()
    }
}