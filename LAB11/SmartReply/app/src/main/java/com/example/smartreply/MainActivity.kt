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
    private var conversationHistory = ""

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
            clearConversation()
        }

        binding.hint0Button.setOnClickListener {
            addSuggestionAsStudentMessage(binding.hint0Button.text.toString())
        }

        binding.hint1Button.setOnClickListener {
            addSuggestionAsStudentMessage(binding.hint1Button.text.toString())
        }

        binding.hint2Button.setOnClickListener {
            addSuggestionAsStudentMessage(binding.hint2Button.text.toString())
        }
    }

    private fun addMessage(text: String) {
        val message = text.trim()

        if (message.isEmpty()) {
            Toast.makeText(this, "Ingrese un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.studentRadioButton.isChecked) {
            addStudentMessage(message)
        } else if (binding.teacherRadioButton.isChecked) {
            addTeacherMessage(message)
        }

        binding.messageText.setText("")
        hideHints()
    }

    private fun addStudentMessage(text: String) {
        conversation.add(
            TextMessage.createForLocalUser(
                text,
                System.currentTimeMillis()
            )
        )

        addMessageToScreen("Estudiante", text)
    }

    private fun addTeacherMessage(text: String) {
        conversation.add(
            TextMessage.createForRemoteUser(
                text,
                System.currentTimeMillis(),
                "Profesor"
            )
        )

        addMessageToScreen("Profesor", text)
    }

    private fun addSuggestionAsStudentMessage(text: String) {
        if (text.trim().isEmpty()) return

        conversation.add(
            TextMessage.createForLocalUser(
                text,
                System.currentTimeMillis()
            )
        )

        addMessageToScreen("Estudiante", text)

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

                hideHints()

                if (result.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                    Toast.makeText(
                        applicationContext,
                        "Lenguaje no soportado. Prueba con mensajes en inglés.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {

                    val suggestions = result.suggestions

                    if (suggestions.isEmpty()) {
                        Toast.makeText(
                            this,
                            "No se generaron sugerencias para este contexto.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnSuccessListener
                    }

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

    private fun addMessageToScreen(sender: String, text: String) {
        if (conversationHistory.isEmpty()) {
            conversationHistory = "$sender: $text"
        } else {
            conversationHistory += "\n$sender: $text"
        }

        binding.conversationText.text = conversationHistory
    }

    private fun clearConversation() {
        conversation.clear()
        conversationHistory = ""

        binding.messageText.setText("")
        binding.conversationText.text = "Aún no hay mensajes."
        binding.errorText.text = ""

        binding.studentRadioButton.isChecked = true

        hideHints()
    }

    private fun hideHints() {
        binding.hint0Button.visibility = View.GONE
        binding.hint1Button.visibility = View.GONE
        binding.hint2Button.visibility = View.GONE
    }
}