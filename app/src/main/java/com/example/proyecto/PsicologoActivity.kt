package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PsicologoActivity : AppCompatActivity() {
    private lateinit var cedulaProfesional: EditText
    private lateinit var spinnerEstudios: Spinner
    private lateinit var spinnerEspecialidad: Spinner
    private lateinit var spinnerUbicacionPsicologo: Spinner
    private lateinit var btnEnviarCuestionario: Button
    private lateinit var btnVolverPsicologo: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psicologo)

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referencias a los elementos del layout
        cedulaProfesional = findViewById(R.id.cedulaProfesional)
        spinnerEstudios = findViewById(R.id.spinnerEstudios)
        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidad)
        spinnerUbicacionPsicologo = findViewById(R.id.spinnerUbicacionPsicologo)
        btnEnviarCuestionario = findViewById(R.id.btnEnviarCuestionario)
        btnVolverPsicologo = findViewById(R.id.btnVolverPsicologo)

        // Configurar Spinner de Estudios Profesionales
        val adapterEstudios = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_estudios, android.R.layout.simple_spinner_item
        )
        adapterEstudios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstudios.adapter = adapterEstudios

        // Configurar Spinner de Especialidad
        val adapterEspecialidad = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_especialidad, android.R.layout.simple_spinner_item
        )
        adapterEspecialidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEspecialidad.adapter = adapterEspecialidad

        // Configurar Spinner de Ubicación
        val adapterUbicacion = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_ubicacion, android.R.layout.simple_spinner_item
        )
        adapterUbicacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUbicacionPsicologo.adapter = adapterUbicacion

        // Acción del botón Enviar
        btnEnviarCuestionario.setOnClickListener {
            val cedula = cedulaProfesional.text.toString().trim()
            val estudios = spinnerEstudios.selectedItem.toString()
            val especialidad = spinnerEspecialidad.selectedItem.toString()
            val ubicacion = spinnerUbicacionPsicologo.selectedItem.toString()

            if (cedula.isEmpty() || estudios == "Seleccione una opción" || especialidad == "Seleccione una opción" || ubicacion == "Seleccione una opción") {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Guardar datos en Firestore
                val userId = mAuth.currentUser?.uid
                if (userId != null) {
                    val formulario = hashMapOf(
                        "cedula" to cedula,
                        "estudios" to estudios,
                        "especialidad" to especialidad,
                        "ubicacion" to ubicacion
                    )

                    db.collection("usuarios").document(userId)
                        .update(
                            mapOf(
                                "formulario" to formulario,
                                "formularioCompletado" to true
                            )
                        )
                        .addOnSuccessListener {
                            Toast.makeText(this, "Formulario guardado exitosamente.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ResultadoPsicologoActivity::class.java)
                            intent.putExtra("cedula", cedula)
                            intent.putExtra("estudios", estudios)
                            intent.putExtra("especialidad", especialidad)
                            intent.putExtra("ubicacion", ubicacion)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al guardar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Error: Usuario no autenticado.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción del botón Volver
        btnVolverPsicologo.setOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
