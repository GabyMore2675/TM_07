package com.example.seguimiento

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SegundaActividad : AppCompatActivity() {

    private var nbActivo = false
    private var nProgressBar: ProgressBar? = null

    companion object {
        const val TIMER_RUNTIME = 10000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad2)

        nProgressBar = findViewById(R.id.progressBar)

        val thread = Thread {
            nbActivo = true
            var tiempo = 0

            try {
                while (nbActivo && tiempo < TIMER_RUNTIME) {
                    Thread.sleep(200)
                    tiempo += 200
                    actualizarProgress(tiempo)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                onContinuar()
            }
        }

        thread.start()
    }

    private fun actualizarProgress(timePassed: Int) {
        runOnUiThread {
            val progress = nProgressBar!!.max * timePassed / TIMER_RUNTIME
            nProgressBar!!.progress = progress
        }
    }

    private fun onContinuar() {
        Log.d("mensajeFinal", "Barra completada")
    }
}