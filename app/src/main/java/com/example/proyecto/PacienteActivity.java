package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PacienteActivity extends AppCompatActivity {

    private Spinner spinnerUbicacion, spinnerDiagnostico;
    private RadioGroup radioGroupDiagnostico;
    private RadioButton radioSi, radioNo;
    private TextView textViewDiagnostico;
    private Button btnEnviarPaciente, btnVolverPaciente;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String nombre; // Variable para guardar el nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Recibir el nombre desde BienvenidaActivity
        nombre = getIntent().getStringExtra("nombre");

        // Referencias a los elementos
        spinnerUbicacion = findViewById(R.id.spinnerUbicacion);
        spinnerDiagnostico = findViewById(R.id.spinnerDiagnostico);
        radioGroupDiagnostico = findViewById(R.id.radioGroupDiagnostico);
        radioSi = findViewById(R.id.radioSi);
        radioNo = findViewById(R.id.radioNo);
        textViewDiagnostico = findViewById(R.id.textViewDiagnostico);
        btnEnviarPaciente = findViewById(R.id.btnEnviarPaciente);
        btnVolverPaciente = findViewById(R.id.btnVolverPaciente);

        // Configurar Spinner de ubicación
        ArrayAdapter<CharSequence> adapterUbicacion = ArrayAdapter.createFromResource(this,
                R.array.opciones_ubicacion, android.R.layout.simple_spinner_item);
        adapterUbicacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUbicacion.setAdapter(adapterUbicacion);

        // Configurar Spinner de diagnóstico
        ArrayAdapter<CharSequence> adapterDiagnostico = ArrayAdapter.createFromResource(this,
                R.array.opciones_diagnostico, android.R.layout.simple_spinner_item);
        adapterDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiagnostico.setAdapter(adapterDiagnostico);

        // Mostrar u ocultar Spinner de diagnóstico según la selección
        radioGroupDiagnostico.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioSi) {
                textViewDiagnostico.setVisibility(View.VISIBLE);
                spinnerDiagnostico.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioNo) {
                textViewDiagnostico.setVisibility(View.GONE);
                spinnerDiagnostico.setVisibility(View.GONE);
            }
        });

        // Acción del botón Enviar
        btnEnviarPaciente.setOnClickListener(v -> {
            String ubicacion = spinnerUbicacion.getSelectedItem().toString();
            String diagnostico = radioSi.isChecked() ? spinnerDiagnostico.getSelectedItem().toString() : "No diagnosticado";

            if (radioSi.isChecked() && (diagnostico.equals("Seleccione un diagnóstico") || diagnostico.isEmpty())) {
                Toast.makeText(PacienteActivity.this, "Por favor, selecciona un diagnóstico.", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar datos en Firestore
                String userId = mAuth.getCurrentUser().getUid();
                Map<String, Object> formulario = new HashMap<>();
                formulario.put("ubicacion", ubicacion);
                formulario.put("diagnostico", diagnostico);

                db.collection("usuarios").document(userId)
                        .update("formulario", formulario, "formularioCompletado", true)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Formulario guardado exitosamente.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PacienteActivity.this, ResultadoActivity.class);
                            intent.putExtra("diagnostico", diagnostico);
                            intent.putExtra("nombre", nombre);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Acción del botón Volver
        btnVolverPaciente.setOnClickListener(v -> {
            Intent intent = new Intent(PacienteActivity.this, BienvenidaActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
