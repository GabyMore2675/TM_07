package com.example.pizza

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView

    private val nombres = arrayOf(
        "Pizza Pepperoni",
        "Pizza Hawaiana",
        "Pizza BBQ",
        "Pizza Vegetariana",
        "Pizza Cuatro Quesos",
        "Pizza Suprema"
    )

    private val descripciones = arrayOf(
        "Pizza clásica con pepperoni y queso.",
        "Pizza con jamón y piña.",
        "Pizza con salsa BBQ y pollo.",
        "Pizza con verduras frescas.",
        "Mezcla de cuatro quesos.",
        "Pizza completa con todo."
    )

    private val imagenes = intArrayOf(
        R.drawable.pizza1,
        R.drawable.pizza2,
        R.drawable.pizza3,
        R.drawable.pizza4,
        R.drawable.pizza5,
        R.drawable.pizza6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)

        val adapter = PizzaAdapter(this, nombres, imagenes)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->

            val view = layoutInflater.inflate(R.layout.dialog_pizza, null)

            val img = view.findViewById<ImageView>(R.id.imgDialog)
            val txtNombre = view.findViewById<TextView>(R.id.txtNombreDialog)
            val txtDesc = view.findViewById<TextView>(R.id.txtDescDialog)

            img.setImageResource(imagenes[position])
            txtNombre.text = nombres[position]
            txtDesc.text = descripciones[position]

            AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Cerrar", null)
                .show()
        }
    }
}