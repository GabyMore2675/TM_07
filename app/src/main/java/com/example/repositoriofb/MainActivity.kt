package com.example.repositoriofb

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:        FirebaseAuth
    private lateinit var db:          FirebaseDatabase
    private lateinit var clasesRef:   DatabaseReference

    private lateinit var spnEscuela:    Spinner
    private lateinit var spnSemestre:   Spinner
    private lateinit var spnSeccion:    Spinner
    private lateinit var spnAsignatura: Spinner
    private lateinit var edtTema:       TextInputEditText

    // Listas en memoria para los spinners
    private val listaEscuelas    = mutableListOf<String>()
    private val listaSemestres   = mutableListOf<String>()
    private val listaAsignaturas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth      = FirebaseAuth.getInstance()
        db        = FirebaseDatabase.getInstance()
        clasesRef = db.getReference("Clases")

        bindViews()
        cargarCatalogos()

        findViewById<Button>(R.id.btnRegistrar).setOnClickListener  { registrarClase() }

        findViewById<Button>(R.id.btnVisualizar).setOnClickListener {
            startActivity(Intent(this, Visualizar::class.java))
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Enlazar vistas con variables
    // ──────────────────────────────────────────────────────────────────────────
    private fun bindViews() {
        spnEscuela    = findViewById(R.id.spnEscuela)
        spnSemestre   = findViewById(R.id.spnSemestre)
        spnSeccion    = findViewById(R.id.spnSeccion)
        spnAsignatura = findViewById(R.id.spnAsignatura)
        edtTema       = findViewById(R.id.edtTema)
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Cargar todos los catálogos desde Firebase en tiempo real
    // ──────────────────────────────────────────────────────────────────────────
    private fun cargarCatalogos() {
        cargarSpinner("EscuelasProfesionales", "nombre", listaEscuelas,    spnEscuela)
        cargarSpinner("Semestres",             "nombre", listaSemestres,   spnSemestre)
        cargarSpinner("Asignaturas",           "nombre", listaAsignaturas, spnAsignatura)

        // Secciones son fijas
        spnSeccion.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("A", "B", "C", "D")
        )
    }

    // Función genérica para cargar un nodo de Firebase en un Spinner
    private fun cargarSpinner(
        nodo:    String,
        campo:   String,
        lista:   MutableList<String>,
        spinner: Spinner
    ) {
        db.getReference(nodo).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                lista.clear()
                snap.children.forEach { child ->
                    child.child(campo).getValue(String::class.java)?.let { lista.add(it) }
                }
                spinner.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    lista.toList()  // copia inmutable para el adapter
                )
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // ──────────────────────────────────────────────────────────────────────────
    // CREATE: registrar una nueva clase en Firebase
    // ──────────────────────────────────────────────────────────────────────────
    private fun registrarClase() {
        val tema = edtTema.text.toString().trim()

        if (tema.isEmpty()) {
            Toast.makeText(this, "Escribe el tema antes de registrar", Toast.LENGTH_SHORT).show()
            return
        }

        // push() genera una clave única para el nuevo nodo (evita colisiones)
        val id = clasesRef.child("Lecciones").push().key ?: return

        val clase = Clase(
            claseid  = id,
            escuela  = spnEscuela.selectedItem?.toString()    ?: "",
            semestre = spnSemestre.selectedItem?.toString()   ?: "",
            seccion  = spnSeccion.selectedItem?.toString()    ?: "",
            area     = spnAsignatura.selectedItem?.toString() ?: "",
            tema     = tema
        )

        clasesRef.child("Lecciones").child(id).setValue(clase)
            .addOnSuccessListener {
                Toast.makeText(this, "✅ Clase registrada", Toast.LENGTH_LONG).show()
                edtTema.text?.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}