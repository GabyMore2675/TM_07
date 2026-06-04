package com.example.autenticacion.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autenticacion.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.util.Log
import com.example.autenticacion.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var semail: String
    private lateinit var spassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {

            binding.loading.visibility = View.VISIBLE

            performLoginOrRegistration()
        }
    }

    private fun performLoginOrRegistration() {

        semail = binding.username.text.toString().trim()
        spassword = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {

            binding.username.error = "Formato inválido de email"
            binding.loading.visibility = View.GONE

        } else if (TextUtils.isEmpty(spassword) || spassword.length < 6) {

            binding.password.error = "El password debe tener al menos 6 caracteres"
            binding.loading.visibility = View.GONE

        } else {

            registerUser()
        }
    }

    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(semail, spassword)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    binding.loading.visibility = View.GONE

                    Toast.makeText(
                        this,
                        "Registro exitoso",
                        Toast.LENGTH_SHORT
                    ).show()

                    abrirMain()

                } else {

                    mAuth.signInWithEmailAndPassword(semail, spassword)
                        .addOnCompleteListener(this) { loginTask ->
                            binding.loading.visibility = View.GONE

                            if (loginTask.isSuccessful) {

                                Toast.makeText(
                                    this,
                                    "Inicio de sesión exitoso",
                                    Toast.LENGTH_SHORT
                                ).show()

                                abrirMain()

                            } else {

                                Toast.makeText(
                                    this,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
    }

    private fun abrirMain() {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("EMAIL", semail)
        startActivity(intent)

        finish()
    }
}