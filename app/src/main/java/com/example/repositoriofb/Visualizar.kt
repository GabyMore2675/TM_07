package com.example.repositoriofb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Visualizar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    private val clases = mutableListOf<Clase>()
    private val listaCompleta = mutableListOf<Clase>()

    private lateinit var adapter: ClaseAdapter

    // 🔥 ADMIN
    private val esAdmin: Boolean
        get() = FirebaseAuth.getInstance().currentUser?.email
            ?.trim()
            ?.lowercase() == "admin@gmail.com"

    // Filtros
    private lateinit var spnEscuela: Spinner
    private lateinit var spnSemestre: Spinner
    private lateinit var spnAsignatura: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        ref = db.getReference("Clases/Lecciones")

        spnEscuela = findViewById(R.id.spnFiltroEscuela)
        spnSemestre = findViewById(R.id.spnFiltroSemestre)
        spnAsignatura = findViewById(R.id.spnFiltroAsignatura)

        configurarRecyclerView()
        escucharCambios()
        configurarListeners()

        findViewById<Button>(R.id.btnLogoutVis).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (esAdmin) finish()
        }
    }

    // ─────────────────────────────────────────────
    // RecyclerView
    // ─────────────────────────────────────────────
    private fun configurarRecyclerView() {
        adapter = ClaseAdapter(
            items = clases,
            esAdmin = esAdmin,
            onEdit = { mostrarDialogoEditar(it) },
            onDelete = { confirmarEliminacion(it) }
        )

        val recycler = findViewById<RecyclerView>(R.id.recyclerClases)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    // ─────────────────────────────────────────────
    // FIREBASE
    // ─────────────────────────────────────────────
    private fun escucharCambios() {

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                listaCompleta.clear()

                snapshot.children.forEach {
                    it.getValue(Clase::class.java)?.let { clase ->
                        listaCompleta.add(clase)
                    }
                }

                cargarSpinners()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Visualizar, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // ─────────────────────────────────────────────
    // CARGAR FILTROS
    // ─────────────────────────────────────────────
    private fun cargarSpinners() {

        val escuelas = mutableListOf("Seleccione escuela")
        val semestres = mutableListOf("Seleccione semestre")
        val asignaturas = mutableListOf("Seleccione asignatura")

        listaCompleta.forEach {
            if (!escuelas.contains(it.escuela)) escuelas.add(it.escuela)
            if (!semestres.contains(it.semestre)) semestres.add(it.semestre)
            if (!asignaturas.contains(it.area)) asignaturas.add(it.area)
        }

        spnEscuela.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, escuelas)
        spnSemestre.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, semestres)
        spnAsignatura.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, asignaturas)
    }

    // ─────────────────────────────────────────────
    // SOLO MOSTRAR SI TODOS LOS FILTROS ESTÁN SELECCIONADOS
    // ─────────────────────────────────────────────
    private fun aplicarFiltros() {

        val escuela = spnEscuela.selectedItem?.toString()
        val semestre = spnSemestre.selectedItem?.toString()
        val asignatura = spnAsignatura.selectedItem?.toString()


        val filtrado = listaCompleta.filter {
            it.escuela == escuela &&
                    it.semestre == semestre &&
                    it.area == asignatura
        }


        clases.addAll(filtrado)
        adapter.notifyDataSetChanged()

        findViewById<TextView>(R.id.tvEmpty).visibility =
            if (clases.isEmpty()) View.VISIBLE else View.GONE
    }

    // ─────────────────────────────────────────────
    // LISTENERS
    // ─────────────────────────────────────────────
    private fun configurarListeners() {

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                aplicarFiltros()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spnEscuela.onItemSelectedListener = listener
        spnSemestre.onItemSelectedListener = listener
        spnAsignatura.onItemSelectedListener = listener
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────
    private fun mostrarDialogoEditar(clase: Clase) {
        val input = EditText(this).apply {
            setText(clase.tema)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar tema")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevo = input.text.toString()
                ref.child(clase.claseid).child("tema").setValue(nuevo)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────
    private fun confirmarEliminacion(clase: Clase) {

        AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("¿Eliminar ${clase.tema}?")
            .setPositiveButton("Sí") { _, _ ->
                ref.child(clase.claseid).removeValue()
            }
            .setNegativeButton("No", null)
            .show()
    }
}