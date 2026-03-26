package com.example.pizza

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PizzaAdapter(
    private val context: Context,
    private val nombres: Array<String>,
    private val imagenes: IntArray
) : BaseAdapter() {

    override fun getCount(): Int = nombres.size

    override fun getItem(position: Int): Any = nombres[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pizza, parent, false)

        val img = view.findViewById<ImageView>(R.id.imgPizza)
        val txt = view.findViewById<TextView>(R.id.txtNombre)

        img.setImageResource(imagenes[position])
        txt.text = nombres[position]

        return view
    }
}