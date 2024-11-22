package com.example.proyecto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoActivity : AppCompatActivity() {
    private lateinit var textViewLink: TextView
    private lateinit var textViewBienvenida: TextView
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        // Referencias a los elementos del layout
        textViewLink = findViewById(R.id.textViewLink)
        textViewBienvenida = findViewById(R.id.textViewBienvenida)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // Obtener diagnóstico y nombre desde el intent
        val diagnostico = intent.getStringExtra("diagnostico") ?: "Diagnóstico no especificado"
        val nombre = intent.getStringExtra("nombre") ?: "Usuario"

        // Mostrar el nombre en el mensaje de bienvenida
        textViewBienvenida.text = "¡Bienvenido a MindEase, $nombre!"

        // Determinar el enlace al video según el diagnóstico
        val videoUrl = obtenerVideoUrl(diagnostico)

        // Configurar el enlace al video
        if (videoUrl.isNotEmpty()) {
            textViewLink.text = "Ver video relacionado"
            textViewLink.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(browserIntent)
            }
        } else {
            textViewLink.text = "No se encontró un video relacionado."
        }

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener {
            val intentCerrarSesion = Intent(this, LoginActivity::class.java)
            intentCerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentCerrarSesion)
            finish()
        }
    }

    private fun obtenerVideoUrl(diagnostico: String): String {
        return when {
            diagnostico.contains("Trauma", true) || diagnostico.contains("Depresión", true) || diagnostico.contains("Ansiedad", true) -> {
                "https://www.youtube.com/watch?v=B5rhpspkyWw"
            }
            diagnostico.contains("Estrés Deportivo", true) || diagnostico.contains("Rehabilitación Emocional", true) || diagnostico.contains("Trabajo en Equipo", true) -> {
                "https://www.youtube.com/watch?v=sowG4gyq-cs"
            }
            diagnostico.contains("Ludopatía", true) || diagnostico.contains("Alcoholismo", true) || diagnostico.contains("Drogadicción", true) -> {
                "https://www.youtube.com/watch?v=qd9lmvDkJOQ"
            }
            diagnostico.contains("Problemas de Aprendizaje", true) || diagnostico.contains("Orientación Vocacional", true) || diagnostico.contains("Intervención en Disciplina", true) -> {
                "https://www.youtube.com/watch?v=im3vc2NzCVc"
            }
            diagnostico.contains("Trastorno del Desarrollo", true) || diagnostico.contains("Problemas de Conducta", true) || diagnostico.contains("Manejo del Bullying", true) -> {
                "https://www.youtube.com/watch?v=FZc7lUl2BIc"
            }
            diagnostico.contains("Conflicto de Pareja", true) || diagnostico.contains("Terapia Sexual", true) || diagnostico.contains("Dinámica Familiar", true) || diagnostico.contains("Comunicación en Pareja", true) -> {
                "https://www.youtube.com/watch?v=k-B_sOd9_ng"
            }
            else -> ""
        }
    }
}
