package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class BienvenidaActivity : AppCompatActivity() {
    private lateinit var textViewBienvenida: TextView
    private lateinit var textViewFormularioEstado: TextView
    private lateinit var btnSoyPaciente: Button
    private lateinit var btnSoyPsicologo: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnDirectorio: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser?.uid

        // Referencias a los elementos del layout
        textViewBienvenida = findViewById(R.id.textViewBienvenida)
        textViewFormularioEstado = findViewById(R.id.textViewFormularioEstado)
        btnSoyPaciente = findViewById(R.id.btnSoyPaciente)
        btnSoyPsicologo = findViewById(R.id.btnSoyPsicologo)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnDirectorio = findViewById(R.id.btnDirectorio)

        // Mostrar nombre del usuario
        val nombre = intent.getStringExtra("nombre") ?: "Usuario"
        textViewBienvenida.text = "¡Bienvenido a MindEase, $nombre!"

        // Consultar el estado del formulario
        userId?.let { id ->
            db.collection("usuarios").document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val formularioCompletado = document.getBoolean("formularioCompletado") ?: false
                        if (formularioCompletado) {
                            textViewFormularioEstado.text = "Formulario contestado"
                            textViewFormularioEstado.visibility = View.VISIBLE
                            btnSoyPaciente.isEnabled = false
                            btnSoyPsicologo.isEnabled = false
                        }
                    }
                }
                .addOnFailureListener {
                    textViewFormularioEstado.text = "Error al obtener estado del formulario"
                    textViewFormularioEstado.visibility = View.VISIBLE
                }
        }

        // Acción del botón "Soy Paciente"
        btnSoyPaciente.setOnClickListener {
            userId?.let { id ->
                db.collection("usuarios").document(id)
                    .update("rol", "paciente")
                    .addOnSuccessListener {
                        val intent = Intent(this, PacienteActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        textViewFormularioEstado.text = "Error al actualizar el rol a paciente"
                        textViewFormularioEstado.visibility = View.VISIBLE
                    }
            }
        }

        // Acción del botón "Soy Psicólogo"
        btnSoyPsicologo.setOnClickListener {
            userId?.let { id ->
                db.collection("usuarios").document(id)
                    .update("rol", "psicologo")
                    .addOnSuccessListener {
                        val intent = Intent(this, PsicologoActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        textViewFormularioEstado.text = "Error al actualizar el rol a psicólogo"
                        textViewFormularioEstado.visibility = View.VISIBLE
                    }
            }
        }

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Acción del botón "Directorio"
        btnDirectorio.setOnClickListener {
            val intent = Intent(this, DirectorioActivity::class.java)
            startActivity(intent)
        }
    }
}
