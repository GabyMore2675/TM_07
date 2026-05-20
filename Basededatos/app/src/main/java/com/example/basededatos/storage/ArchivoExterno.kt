package com.example.basededatos.storage

import android.content.Context
import java.io.File

class ArchivoExterno(
    private val context: Context
) {

    fun guardarArchivo(texto: String) {

        val carpeta = context.getExternalFilesDir(null)
        val archivo = File(carpeta, "datos.txt")

        archivo.writeText(texto)
    }

    fun leerArchivo(): String {

        val carpeta = context.getExternalFilesDir(null)
        val archivo = File(carpeta, "datos.txt")

        return if (archivo.exists()) {
            archivo.readText()
        } else {
            "Archivo no existe"
        }
    }
}