package com.example.repositoriofb

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EstudiantesActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtCarrera: EditText
    private lateinit var edtCurso: EditText
    private lateinit var btnGuardar: Button

    private lateinit var recycler: RecyclerView

    private lateinit var estudiantesRef: DatabaseReference

    private val lista = mutableListOf<Estudiante>()

    private lateinit var adapter: EstudianteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_estudiantes)

        edtNombre = findViewById(R.id.edtNombre)
        edtCarrera = findViewById(R.id.edtCarrera)
        edtCurso = findViewById(R.id.edtCurso)
        btnGuardar = findViewById(R.id.btnGuardar)

        recycler = findViewById(R.id.recyclerEstudiantes)

        estudiantesRef = FirebaseDatabase
            .getInstance()
            .getReference("Estudiantes")

        adapter = EstudianteAdapter(
            lista,
            { estudiante -> editar(estudiante) },
            { estudiante -> eliminar(estudiante) }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnGuardar.setOnClickListener {
            guardar()
        }

        leerDatos()
    }

    private fun guardar() {

        val nombre = edtNombre.text.toString()
        val carrera = edtCarrera.text.toString()
        val curso = edtCurso.text.toString()

        val id = estudiantesRef.push().key ?: return

        val estudiante = Estudiante(
            id,
            nombre,
            carrera,
            curso
        )

        estudiantesRef.child(id).setValue(estudiante)

        edtNombre.text.clear()
        edtCarrera.text.clear()
        edtCurso.text.clear()
    }

    private fun leerDatos() {

        estudiantesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                lista.clear()

                for (dato in snapshot.children) {

                    val estudiante =
                        dato.getValue(Estudiante::class.java)

                    estudiante?.let {
                        lista.add(it)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun editar(estudiante: Estudiante) {

        val nuevosDatos = mapOf<String, Any>(
            "curso" to "Curso Actualizado"
        )

        estudiantesRef
            .child(estudiante.id)
            .updateChildren(nuevosDatos)
    }

    private fun eliminar(estudiante: Estudiante) {

        estudiantesRef
            .child(estudiante.id)
            .removeValue()
    }
}