package com.norbel.catalogodeproductos.catalogo

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbel.catalogodeproductos.R

class CatalogoActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var lista: MutableList<Producto>
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        prefs = getSharedPreferences("productos", MODE_PRIVATE)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCantidad = findViewById<EditText>(R.id.etCantidad)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerProductos)

        // Cargar datos guardados
        lista = cargarProductos()
        adapter = ProductoAdapter(lista)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val cantidadStr = etCantidad.text.toString()
            val precioStr = etPrecio.text.toString()

            // 🔴 VALIDACIÓN
            if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cantidad = cantidadStr.toInt()
            val precio = precioStr.toDouble()

            val producto = Producto(nombre, cantidad, precio)
            lista.add(producto)

            guardarProductos(lista)
            adapter.notifyDataSetChanged()

            Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show()

            // 🧹 LIMPIAR CAMPOS
            etNombre.text.clear()
            etCantidad.text.clear()
            etPrecio.text.clear()
        }
    }

    private fun guardarProductos(lista: List<Producto>) {
        val data = lista.joinToString(";") {
            "${it.nombre},${it.cantidad},${it.precio}"
        }
        prefs.edit().putString("lista", data).apply()
    }

    private fun cargarProductos(): MutableList<Producto> {
        val lista = mutableListOf<Producto>()
        val data = prefs.getString("lista", "") ?: ""

        if (data.isNotEmpty()) {
            val productos = data.split(";")
            for (p in productos) {
                val partes = p.split(",")
                if (partes.size == 3) {
                    lista.add(
                        Producto(
                            partes[0],
                            partes[1].toInt(),
                            partes[2].toDouble()
                        )
                    )
                }
            }
        }
        return lista
    }
}