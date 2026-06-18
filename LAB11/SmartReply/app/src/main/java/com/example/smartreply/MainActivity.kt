package com.example.smartreply

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartreply.databinding.ActivityMainBinding
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var conversation = ArrayList<TextMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener {
            addMessage(binding.messageText.text.toString())
        }

        binding.hintsButton.setOnClickListener {
            getHints()
        }

        binding.clearButton.setOnClickListener {
            conversation.clear()

            binding.hint0Button.visibility = View.GONE
            binding.hint1Button.visibility = View.GONE
            binding.hint2Button.visibility = View.GONE

            binding.messageText.setText("")
            binding.nameText.setText("")
            binding.errorText.text = ""
        }

        binding.hint0Button.setOnClickListener {
            addLocalMessage(binding.hint0Button.text.toString())
        }

        binding.hint1Button.setOnClickListener {
            addLocalMessage(binding.hint1Button.text.toString())
        }

        binding.hint2Button.setOnClickListener {
            addLocalMessage(binding.hint2Button.text.toString())
        }
    }

    private fun addMessage(text: String) {
        val name = binding.nameText.text.toString().trim()

        if (text.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        conversation.add(
            TextMessage.createForRemoteUser(
                text,
                System.currentTimeMillis(),
                name
            )
        )

        hideHints()
    }

    private fun addLocalMessage(text: String) {
        if (text.trim().isEmpty()) return

        conversation.add(
            TextMessage.createForLocalUser(
                text,
                System.currentTimeMillis()
            )
        )

        binding.messageText.setText("")
        hideHints()
    }

    private fun getHints() {
        if (conversation.isEmpty()) {
            Toast.makeText(this, "Primero ingrese un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        val smartReply = SmartReply.getClient()

        smartReply.suggestReplies(conversation)
            .addOnSuccessListener { result ->

                if (result.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                    Toast.makeText(
                        applicationContext,
                        "Lenguaje no soportado. Prueba con mensajes en inglés.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {

                    val suggestions = result.suggestions

                    if (suggestions.isNotEmpty()) {
                        binding.hint0Button.text = suggestions[0].text
                        binding.hint0Button.visibility = View.VISIBLE
                    }

                    if (suggestions.size > 1) {
                        binding.hint1Button.text = suggestions[1].text
                        binding.hint1Button.visibility = View.VISIBLE
                    }

                    if (suggestions.size > 2) {
                        binding.hint2Button.text = suggestions[2].text
                        binding.hint2Button.visibility = View.VISIBLE
                    }
                }
            }
            .addOnFailureListener { exception ->
                binding.errorText.text = exception.toString()
            }
    }

    private fun hideHints() {
        binding.hint0Button.visibility = View.GONE
        binding.hint1Button.visibility = View.GONE
        binding.hint2Button.visibility = View.GONE
    }
}