package com.example.repositoriofb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EstudianteAdapter(
    private val lista: MutableList<Estudiante>,
    private val onEditar: (Estudiante) -> Unit,
    private val onEliminar: (Estudiante) -> Unit
) : RecyclerView.Adapter<EstudianteAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvCarrera: TextView = v.findViewById(R.id.tvCarrera)
        val tvCurso: TextView = v.findViewById(R.id.tvCurso)

        val btnEditar: Button = v.findViewById(R.id.btnEditar)
        val btnEliminar: Button = v.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val estudiante = lista[position]

        holder.tvNombre.text = estudiante.nombre
        holder.tvCarrera.text = estudiante.carrera
        holder.tvCurso.text = estudiante.curso

        holder.btnEditar.setOnClickListener {
            onEditar(estudiante)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminar(estudiante)
        }
    }
}