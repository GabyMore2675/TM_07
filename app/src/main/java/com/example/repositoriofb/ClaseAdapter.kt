package com.example.repositoriofb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClaseAdapter(
    private val items:    List<Clase>,
    private val esAdmin:  Boolean,          // true = admin, false = alumno
    private val onEdit:   (Clase) -> Unit,  // callback para editar
    private val onDelete: (Clase) -> Unit   // callback para eliminar
) : RecyclerView.Adapter<ClaseAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvEscuela:     TextView     = view.findViewById(R.id.tvEscuela)
        val tvSemestre:    TextView     = view.findViewById(R.id.tvSemestre)
        val tvSeccion:     TextView     = view.findViewById(R.id.tvSeccion)
        val tvAsignatura:  TextView     = view.findViewById(R.id.tvAsignatura)
        val tvTema:        TextView     = view.findViewById(R.id.tvTema)
        val layoutBotones: LinearLayout = view.findViewById(R.id.layoutBotones)
        val btnEditar:     Button       = view.findViewById(R.id.btnEditar)
        val btnEliminar:   Button       = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clase, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val c = items[position]

        h.tvEscuela.text    = c.escuela
        h.tvSemestre.text   = c.semestre
        h.tvSeccion.text    = "Sección ${c.seccion}"
        h.tvAsignatura.text = c.area
        h.tvTema.text       = c.tema

        // Mostrar botones solo si el usuario es administrador
        h.layoutBotones.visibility = if (esAdmin) View.VISIBLE else View.GONE

        h.btnEditar.setOnClickListener   { onEdit(c) }
        h.btnEliminar.setOnClickListener { onDelete(c) }
    }
}