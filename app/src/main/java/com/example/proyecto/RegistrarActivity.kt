package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class RegistrarActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var rgRoles: RadioGroup
    private lateinit var btnRegistrar: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referenciar los campos
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextContrasena = findViewById(R.id.editTextContrasena)
        rgRoles = findViewById(R.id.rgRoles)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Acción del botón "Registrar"
        btnRegistrar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val correo = editTextCorreo.text.toString().trim()
            val telefono = editTextTelefono.text.toString().trim()
            val contrasena = editTextContrasena.text.toString().trim()

            // Validación de campos
            if (!validarCampos(nombre, correo, telefono, contrasena)) {
                return@setOnClickListener
            }

            // Determinar el rol seleccionado
            val selectedRoleId = rgRoles.checkedRadioButtonId
            if (selectedRoleId == -1) {
                Toast.makeText(this, "Por favor, selecciona un rol.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rol = findViewById<RadioButton>(selectedRoleId).text.toString()
                .lowercase(Locale.getDefault())

            // Registrar usuario en Firebase Authentication
            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid

                        if (userId != null) {
                            // Guardar datos en Firestore
                            val userData = hashMapOf(
                                "nombre" to nombre,
                                "correo" to correo,
                                "telefono" to telefono,
                                "rol" to rol,
                                "formularioCompletado" to false
                            )

                            db.collection("usuarios").document(userId).set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Error al guardar datos: ${e.message}",
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
                            "Error al registrar usuario: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // Método para validar los campos
    private fun validarCampos(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String
    ): Boolean {
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, completa el campo de nombre.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidEmail(correo)) {
            Toast.makeText(
                this,
                "El correo debe ser válido y terminar en .com, .com.mx o .net.mx.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (!isValidPhone(telefono)) {
            Toast.makeText(this, "El número debe tener 10 dígitos.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contrasena.isEmpty() || contrasena.length < 6) {
            Toast.makeText(
                this,
                "La contraseña debe tener al menos 6 caracteres.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }

    // Validar correo electrónico con dominios específicos
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.(com|com\\.mx|net\\.mx)$"
        return email.matches(emailPattern.toRegex())
    }

    // Validar número de teléfono (10 dígitos)
    private fun isValidPhone(phone: String): Boolean {
        return phone.matches("^\\d{10}$".toRegex())
    }
}
