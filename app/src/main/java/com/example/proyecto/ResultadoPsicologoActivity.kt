package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoPsicologoActivity : AppCompatActivity() {
    private lateinit var textViewCedula: TextView
    private lateinit var textViewEstudios: TextView
    private lateinit var textViewEspecialidad: TextView
    private lateinit var textViewUbicacion: TextView
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_psicologo)

        // Referencias a los elementos del layout
        textViewCedula = findViewById(R.id.textViewCedula)
        textViewEstudios = findViewById(R.id.textViewEstudios)
        textViewEspecialidad = findViewById(R.id.textViewEspecialidad)
        textViewUbicacion = findViewById(R.id.textViewUbicacion)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // Obtener los datos del intent
        val cedula = intent.getStringExtra("cedula") ?: "N/A"
        val estudios = intent.getStringExtra("estudios") ?: "N/A"
        val especialidad = intent.getStringExtra("especialidad") ?: "N/A"
        val ubicacion = intent.getStringExtra("ubicacion") ?: "N/A"

        // Mostrar los datos en los TextView
        textViewCedula.text = "Cédula: $cedula"
        textViewEstudios.text = "Estudios: $estudios"
        textViewEspecialidad.text = "Especialidad: $especialidad"
        textViewUbicacion.text = "Ubicación: $ubicacion"

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener {
            val intentCerrarSesion = Intent(this, LoginActivity::class.java)
            intentCerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentCerrarSesion)
            finish()
        }
    }
}
