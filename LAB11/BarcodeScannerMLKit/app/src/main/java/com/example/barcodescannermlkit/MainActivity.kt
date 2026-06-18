package com.example.barcodescannermlkit

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.common.InputImage

class MainActivity : AppCompatActivity() {

    private lateinit var scanButton: Button
    private lateinit var galleryButton: Button
    private lateinit var resultText: TextView
    private lateinit var typeText: TextView

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                scanBarcodeFromGallery(uri)
            } else {
                Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        scanButton = findViewById(R.id.scanButton)
        galleryButton = findViewById(R.id.galleryButton)
        resultText = findViewById(R.id.resultText)
        typeText = findViewById(R.id.typeText)

        scanButton.setOnClickListener {
            scanBarcodeWithCamera()
        }

        galleryButton.setOnClickListener {
            openGallery()
        }
    }

    private fun scanBarcodeWithCamera() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E
            )
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(this, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue = barcode.rawValue ?: "Sin valor detectado"

                resultText.text = rawValue
                typeText.text = "Tipo de código: ${getBarcodeFormatName(barcode.format)}"
            }
            .addOnCanceledListener {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                resultText.text = "Error al escanear con cámara"
                typeText.text = e.message ?: "Error desconocido"
            }
    }

    private fun openGallery() {
        imagePickerLauncher.launch("image/*")
    }

    private fun scanBarcodeFromGallery(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isEmpty()) {
                        resultText.text = "No se detectó ningún código"
                        typeText.text = "Intenta usar una imagen más clara o con mejor iluminación."
                    } else {
                        val barcode = barcodes[0]
                        val rawValue = barcode.rawValue ?: "Sin valor detectado"

                        resultText.text = rawValue
                        typeText.text = "Tipo de código: ${getBarcodeFormatName(barcode.format)}"
                    }
                }
                .addOnFailureListener { e ->
                    resultText.text = "Error al analizar la imagen"
                    typeText.text = e.message ?: "Error desconocido"
                }

        } catch (e: Exception) {
            resultText.text = "Error al cargar la imagen"
            typeText.text = e.message ?: "No se pudo procesar la imagen seleccionada"
        }
    }

    private fun getBarcodeFormatName(format: Int): String {
        return when (format) {
            Barcode.FORMAT_QR_CODE -> "QR Code"
            Barcode.FORMAT_CODE_128 -> "Code 128"
            Barcode.FORMAT_EAN_13 -> "EAN-13"
            Barcode.FORMAT_EAN_8 -> "EAN-8"
            Barcode.FORMAT_UPC_A -> "UPC-A"
            Barcode.FORMAT_UPC_E -> "UPC-E"
            else -> "Formato desconocido"
        }
    }
}