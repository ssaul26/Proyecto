package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PacienteActivity : AppCompatActivity() {
    private lateinit var spinnerUbicacion: Spinner
    private lateinit var spinnerDiagnostico: Spinner
    private lateinit var radioGroupDiagnostico: RadioGroup
    private lateinit var radioSi: RadioButton
    private lateinit var radioNo: RadioButton
    private lateinit var textViewDiagnostico: TextView
    private lateinit var btnEnviarPaciente: Button
    private lateinit var btnVolverPaciente: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var nombre: String? = null // Variable para guardar el nombre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Recibir el nombre desde BienvenidaActivity
        nombre = intent.getStringExtra("nombre")

        // Referencias a los elementos
        spinnerUbicacion = findViewById(R.id.spinnerUbicacion)
        spinnerDiagnostico = findViewById(R.id.spinnerDiagnostico)
        radioGroupDiagnostico = findViewById(R.id.radioGroupDiagnostico)
        radioSi = findViewById(R.id.radioSi)
        radioNo = findViewById(R.id.radioNo)
        textViewDiagnostico = findViewById(R.id.textViewDiagnostico)
        btnEnviarPaciente = findViewById(R.id.btnEnviarPaciente)
        btnVolverPaciente = findViewById(R.id.btnVolverPaciente)

        // Configurar Spinner de ubicación
        val adapterUbicacion = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_ubicacion, android.R.layout.simple_spinner_item
        )
        adapterUbicacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUbicacion.adapter = adapterUbicacion

        // Configurar Spinner de diagnóstico
        val adapterDiagnostico = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_diagnostico, android.R.layout.simple_spinner_item
        )
        adapterDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDiagnostico.adapter = adapterDiagnostico

        // Mostrar u ocultar Spinner de diagnóstico según la selección
        radioGroupDiagnostico.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioSi) {
                textViewDiagnostico.visibility = View.VISIBLE
                spinnerDiagnostico.visibility = View.VISIBLE
            } else if (checkedId == R.id.radioNo) {
                textViewDiagnostico.visibility = View.GONE
                spinnerDiagnostico.visibility = View.GONE
            }
        }

        // Acción del botón Enviar
        btnEnviarPaciente.setOnClickListener {
            val ubicacion = spinnerUbicacion.selectedItem.toString()
            val diagnostico = if (radioSi.isChecked) spinnerDiagnostico.selectedItem.toString() else "No diagnosticado"

            if (radioSi.isChecked && (diagnostico == "Seleccione un diagnóstico" || diagnostico.isEmpty())) {
                Toast.makeText(
                    this,
                    "Por favor, selecciona un diagnóstico.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Guardar datos en Firestore
                val userId = mAuth.currentUser?.uid
                if (userId != null) {
                    val formulario = hashMapOf(
                        "ubicacion" to ubicacion,
                        "diagnostico" to diagnostico
                    )

                    db.collection("usuarios").document(userId)
                        .update(
                            mapOf(
                                "formulario" to formulario,
                                "formularioCompletado" to true
                            )
                        )
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Formulario guardado exitosamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, ResultadoActivity::class.java)
                            intent.putExtra("diagnostico", diagnostico)
                            intent.putExtra("nombre", nombre)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Error al guardar los datos: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(this, "Error: Usuario no autenticado.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción del botón Volver
        btnVolverPaciente.setOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
