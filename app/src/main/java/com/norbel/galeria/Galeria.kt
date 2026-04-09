package com.norbel.galeria
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbel.usopreferencias.R

class Galeria : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val imagenes = intArrayOf(
        R.drawable.pic01, R.drawable.pic02,
        R.drawable.pic03, R.drawable.pic04,
        R.drawable.pic05, R.drawable.pic06,
        R.drawable.pic07, R.drawable.pic08
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)
        imageView = findViewById(R.id.imageView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ImagenAdapter(imagenes) { position ->
            // Mostrar la imagen seleccionada en grande
            imageView.setImageResource(imagenes[position])
        }
        recyclerView.adapter = adapter
    }
}