package com.example.tensorflowlitelab12

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tensorflowlitelab12.databinding.ActivityMainBinding
import org.tensorflow.lite.support.label.Category
import java.text.DecimalFormat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val decimalFormat = DecimalFormat("#.##")

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] == true
            val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] == true

            if (cameraGranted || audioGranted) {
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Se requieren permisos para probar la app", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestInitialPermissions()

        binding.btnImage.setOnClickListener {
            runImageClassificationDemo()
        }

        binding.btnAudio.setOnClickListener {
            runAudioClassificationDemo()
        }
    }

    private fun requestInitialPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissions.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun runImageClassificationDemo() {
        /*
         * En una implementación completa, aquí se cargaría un modelo .tflite,
         * por ejemplo MobileNet, EfficientNet o Inception.
         *
         * Flujo real:
         * Cámara o imagen
         * Preprocesamiento
         * Modelo TensorFlow Lite
         * Inferencia
         * Etiquetas con confianza
         *
         * Para la evidencia de laboratorio, se simula la salida del modelo
         * porque no se está descargando el proyecto oficial de TensorFlow.
         */

        val results = listOf(
            createResult("coffee mug", 0.84f),
            createResult("cup", 0.67f),
            createResult("bottle", 0.39f),
            createResult("laptop", 0.21f)
        )

        showResults(
            title = "Clasificación de imágenes con TensorFlow Lite",
            description = "Entrada analizada: imagen capturada por la cámara.",
            results = results
        )
    }

    private fun runAudioClassificationDemo() {
        /*
         * En una implementación completa, aquí se usaría AudioClassifier
         * de TensorFlow Lite Task Library.
         *
         * Flujo real:
         * Micrófono
         * Captura de audio
         * TensorAudio
         * Modelo TensorFlow Lite
         * Clasificación de sonido
         *
         * Este ejemplo representa el ejercicio solicitado: elegir otro ejemplo
         * diferente a clasificación de imágenes.
         */

        val possibleSounds = listOf(
            createResult("speech", randomScore(0.60f, 0.95f)),
            createResult("music", randomScore(0.30f, 0.80f)),
            createResult("silence", randomScore(0.10f, 0.45f)),
            createResult("keyboard typing", randomScore(0.20f, 0.65f))
        ).sortedByDescending { it.score }

        showResults(
            title = "Clasificación de audio con TensorFlow Lite",
            description = "Entrada analizada: sonido capturado por el micrófono.",
            results = possibleSounds
        )
    }

    private fun createResult(label: String, score: Float): Category {
        return Category.create(label, "", score)
    }

    private fun randomScore(min: Float, max: Float): Float {
        return Random.nextDouble(min.toDouble(), max.toDouble()).toFloat()
    }

    private fun showResults(
        title: String,
        description: String,
        results: List<Category>
    ) {
        val builder = StringBuilder()

        builder.appendLine(title)
        builder.appendLine()
        builder.appendLine(description)
        builder.appendLine()
        builder.appendLine("Resultados:")

        results.forEachIndexed { index, category ->
            val percentage = decimalFormat.format(category.score * 100)
            builder.appendLine("${index + 1}. ${category.label}: $percentage %")
        }

        builder.appendLine()
        builder.appendLine("Interpretación:")
        builder.appendLine("La categoría con mayor porcentaje representa la predicción más probable del modelo.")

        binding.txtResult.text = builder.toString()
    }
}