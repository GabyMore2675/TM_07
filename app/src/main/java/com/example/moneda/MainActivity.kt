package com.example.moneda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var spinnerOrigen: Spinner
    private lateinit var spinnerDestino: Spinner
    private lateinit var btnConvertir: Button
    private lateinit var textResultado: TextView

    private val monedas = arrayOf(
        "Soles (PEN)",
        "Dólares (USD)",
        "Euro (EUR)",
        "Libra (GBP)",
        "Rupia (INR)",
        "Real (BRL)",
        "Peso (MXN)",
        "Yuan (CNY)",
        "Yen (JPY)"
    )

    private val tasas = mapOf(
        "Soles (PEN)" to 1.0,
        "Dólares (USD)" to 3.65,
        "Euro (EUR)" to 4.0,
        "Libra (GBP)" to 4.6,
        "Rupia (INR)" to 0.044,
        "Real (BRL)" to 0.74,
        "Peso (MXN)" to 0.21,
        "Yuan (CNY)" to 0.51,
        "Yen (JPY)" to 0.025
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCantidad = findViewById(R.id.editTextCantidad)
        spinnerOrigen = findViewById(R.id.spinnerOrigen)
        spinnerDestino = findViewById(R.id.spinnerDestino)
        btnConvertir = findViewById(R.id.btnConvertir)
        textResultado = findViewById(R.id.textResultado)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerOrigen.adapter = adapter
        spinnerDestino.adapter = adapter

        btnConvertir.setOnClickListener {
            convertir()
        }
    }

    private fun convertir() {
        val texto = editTextCantidad.text.toString()

        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = texto.toDoubleOrNull()
        if (cantidad == null) {
            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val origen = spinnerOrigen.selectedItem.toString()
        val destino = spinnerDestino.selectedItem.toString()

        val valorEnSoles = cantidad * tasas[origen]!!
        val resultado = valorEnSoles / tasas[destino]!!

        val textoFinal = String.format(
            "%.2f %s = %.2f %s",
            cantidad, origen, resultado, destino
        )

        textResultado.text = textoFinal
    }
}