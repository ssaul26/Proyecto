package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var lblRegistrar: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referencias a los campos del layout
        editTextCorreo = findViewById(R.id.txtUsuario)
        editTextContrasena = findViewById(R.id.txtContraseña)
        btnIniciarSesion = findViewById(R.id.btnIngresar)
        lblRegistrar = findViewById(R.id.lblRegistrar)

        // Acción para "Regístrate"
        lblRegistrar.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        // Acción del botón "Iniciar sesión"
        btnIniciarSesion.setOnClickListener {
            val correo = editTextCorreo.text.toString()
            val contrasena = editTextContrasena.text.toString()

            // Validación de campos vacíos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Autenticar usuario con Firebase Authentication
            mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid
                        if (userId != null) {
                            // Obtener datos del usuario desde Firestore
                            db.collection("usuarios").document(userId).get()
                                .addOnSuccessListener { document: DocumentSnapshot ->
                                    if (document.exists()) {
                                        val nombre = document.getString("nombre")
                                        val intent = Intent(this, BienvenidaActivity::class.java)
                                        intent.putExtra("nombre", nombre)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Usuario no encontrado en Firestore.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Error al obtener datos: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "Error: no se pudo obtener el ID del usuario.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Error al iniciar sesión: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
