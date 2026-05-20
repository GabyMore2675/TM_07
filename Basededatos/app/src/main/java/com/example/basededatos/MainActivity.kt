package com.example.basededatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.example.basededatos.data.AppDatabase
import com.example.basededatos.data.Articulo
import com.example.basededatos.databinding.ActivityMainBinding
import com.example.basededatos.repository.ArticuloRepository
import com.example.basededatos.storage.ArchivoExterno
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: ArticuloRepository
    private lateinit var archivoExterno: ArchivoExterno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getInstance(this).articuloDao()

        repository = ArticuloRepository(dao)
        archivoExterno = ArchivoExterno(this)

        observarArticulos()

        binding.btnRegistrar.setOnClickListener {
            registrar()
        }

        binding.btnBuscar.setOnClickListener {
            buscar()
        }

        binding.btnModificar.setOnClickListener {
            modificar()
        }

        binding.btnEliminar.setOnClickListener {
            eliminar()
        }
    }

    private fun observarArticulos() {

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                repository.listarTodos().collect { lista ->

                    val texto = lista.joinToString("\n") {
                        "${it.codigo} - ${it.descripcion} - ${it.precio}"
                    }

                    archivoExterno.guardarArchivo(texto)
                }
            }
        }
    }

    private fun registrar() {

        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()

        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Complete todos los campos")
            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            descripcion,
            precio.toDouble()
        )

        lifecycleScope.launch {
            try {
                repository.insertar(articulo)
                limpiarCampos()
                toast("Registrado")
            } catch (e: Exception) {
                toast("Artículo ya existe")
            }
        }
    }

    private fun buscar() {

        val codigo = binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {
            toast("Ingrese código")
            return
        }

        lifecycleScope.launch {

            val articulo =
                repository.buscarPorCodigo(codigo.toInt())

            if (articulo != null) {

                binding.txtDescripcion
                    .setText(articulo.descripcion)

                binding.txtPrecio
                    .setText(articulo.precio.toString())

            } else {
                toast("No existe")
            }
        }
    }

    private fun modificar() {

        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()

        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Complete todos los campos")
            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            descripcion,
            precio.toDouble()
        )

        lifecycleScope.launch {

            val filas = repository.actualizar(articulo)

            if (filas == 1) {
                toast("Actualizado")
            } else {
                toast("No existe")
            }
        }
    }

    private fun eliminar() {

        val codigo = binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {
            toast("Ingrese código")
            return
        }

        lifecycleScope.launch {

            val filas =
                repository.eliminarPorCodigo(codigo.toInt())

            limpiarCampos()

            if (filas == 1) {
                toast("Eliminado")
            } else {
                toast("No existe")
            }
        }
    }

    private fun limpiarCampos() {
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
    }

    private fun toast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}