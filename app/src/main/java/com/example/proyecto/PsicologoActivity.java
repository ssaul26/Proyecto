package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PsicologoActivity extends AppCompatActivity {

    private EditText cedulaProfesional;
    private Spinner spinnerEstudios, spinnerEspecialidad, spinnerUbicacionPsicologo;
    private Button btnEnviarCuestionario, btnVolverPsicologo;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psicologo);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Referencias a los elementos del layout
        cedulaProfesional = findViewById(R.id.cedulaProfesional);
        spinnerEstudios = findViewById(R.id.spinnerEstudios);
        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidad);
        spinnerUbicacionPsicologo = findViewById(R.id.spinnerUbicacionPsicologo);
        btnEnviarCuestionario = findViewById(R.id.btnEnviarCuestionario);
        btnVolverPsicologo = findViewById(R.id.btnVolverPsicologo);

        // Configurar Spinner de Estudios Profesionales
        ArrayAdapter<CharSequence> adapterEstudios = ArrayAdapter.createFromResource(this,
                R.array.opciones_estudios, android.R.layout.simple_spinner_item);
        adapterEstudios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstudios.setAdapter(adapterEstudios);

        // Configurar Spinner de Especialidad
        ArrayAdapter<CharSequence> adapterEspecialidad = ArrayAdapter.createFromResource(this,
                R.array.opciones_especialidad, android.R.layout.simple_spinner_item);
        adapterEspecialidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecialidad.setAdapter(adapterEspecialidad);

        // Configurar Spinner de Ubicación
        ArrayAdapter<CharSequence> adapterUbicacion = ArrayAdapter.createFromResource(this,
                R.array.opciones_ubicacion, android.R.layout.simple_spinner_item);
        adapterUbicacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUbicacionPsicologo.setAdapter(adapterUbicacion);

        // Acción del botón Enviar
        btnEnviarCuestionario.setOnClickListener(v -> {
            String cedula = cedulaProfesional.getText().toString();
            String estudios = spinnerEstudios.getSelectedItem().toString();
            String especialidad = spinnerEspecialidad.getSelectedItem().toString();
            String ubicacion = spinnerUbicacionPsicologo.getSelectedItem().toString();

            if (cedula.isEmpty() || estudios.isEmpty() || especialidad.isEmpty() || ubicacion.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar datos en Firestore
                String userId = mAuth.getCurrentUser().getUid();
                Map<String, Object> formulario = new HashMap<>();
                formulario.put("cedula", cedula);
                formulario.put("estudios", estudios);
                formulario.put("especialidad", especialidad);
                formulario.put("ubicacion", ubicacion);

                db.collection("usuarios").document(userId)
                        .update("formulario", formulario, "formularioCompletado", true)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Formulario guardado exitosamente.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PsicologoActivity.this, ResultadoPsicologoActivity.class);
                            intent.putExtra("cedula", cedula);
                            intent.putExtra("estudios", estudios);
                            intent.putExtra("especialidad", especialidad);
                            intent.putExtra("ubicacion", ubicacion);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Acción del botón Volver
        btnVolverPsicologo.setOnClickListener(v -> {
            Intent intent = new Intent(PsicologoActivity.this, BienvenidaActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
