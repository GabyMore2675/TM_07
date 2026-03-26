package com.example.proyectokotlin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTemp: EditText
    private lateinit var radioCtoF: RadioButton
    private lateinit var radioFtoC: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTemp = findViewById(R.id.editTextTemp)
        radioCtoF = findViewById(R.id.radioCtoF)
        radioFtoC = findViewById(R.id.radioFtoC)
    }

    private fun convertirTemperatura() {
        val texto = editTextTemp.text.toString().trim()

        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingrese una temperatura", Toast.LENGTH_LONG).show()
            return
        }

        val temp = texto.toFloatOrNull()
        if (temp == null) {
            Toast.makeText(this, "Número inválido", Toast.LENGTH_LONG).show()
            return
        }

        when {
            radioCtoF.isChecked -> {
                val resultado = (temp * 9/5) + 32
                Toast.makeText(this,
                    "%.2f °C = %.2f °F".format(temp, resultado),
                    Toast.LENGTH_LONG).show()
            }

            radioFtoC.isChecked -> {
                val resultado = (temp - 32) * 5/9
                Toast.makeText(this,
                    "%.2f °F = %.2f °C".format(temp, resultado),
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun miClicManejador(view: View) {
        if (view.id == R.id.btnConvertir) {
            convertirTemperatura()
        }
    }
}