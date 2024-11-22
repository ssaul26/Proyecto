package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BienvenidaActivity extends AppCompatActivity {

    private TextView textViewBienvenida, textViewFormularioEstado;
    private Button btnSoyPaciente, btnSoyPsicologo, btnCerrarSesion, btnDirectorio;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        // Referencias a los elementos del layout
        textViewBienvenida = findViewById(R.id.textViewBienvenida);
        textViewFormularioEstado = findViewById(R.id.textViewFormularioEstado);
        btnSoyPaciente = findViewById(R.id.btnSoyPaciente);
        btnSoyPsicologo = findViewById(R.id.btnSoyPsicologo);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnDirectorio = findViewById(R.id.btnDirectorio);

        // Mostrar nombre del usuario
        String nombre = getIntent().getStringExtra("nombre");
        textViewBienvenida.setText("¡Bienvenido a MindEase, " + nombre + "!");

        // Consultar el estado del formulario
        db.collection("usuarios").document(userId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Boolean formularioCompletado = document.getBoolean("formularioCompletado");
                        if (formularioCompletado != null && formularioCompletado) {
                            textViewFormularioEstado.setText("Formulario contestado");
                            textViewFormularioEstado.setVisibility(TextView.VISIBLE);
                            btnSoyPaciente.setEnabled(false);
                            btnSoyPsicologo.setEnabled(false);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    textViewFormularioEstado.setText("Error al obtener estado del formulario");
                    textViewFormularioEstado.setVisibility(TextView.VISIBLE);
                });

        // Acción del botón "Soy Paciente"
        btnSoyPaciente.setOnClickListener(v -> {
            db.collection("usuarios").document(userId)
                    .update("rol", "paciente")
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(BienvenidaActivity.this, PacienteActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        textViewFormularioEstado.setText("Error al actualizar el rol a paciente");
                        textViewFormularioEstado.setVisibility(TextView.VISIBLE);
                    });
        });

        // Acción del botón "Soy Psicólogo"
        btnSoyPsicologo.setOnClickListener(v -> {
            db.collection("usuarios").document(userId)
                    .update("rol", "psicologo")
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(BienvenidaActivity.this, PsicologoActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        textViewFormularioEstado.setText("Error al actualizar el rol a psicólogo");
                        textViewFormularioEstado.setVisibility(TextView.VISIBLE);
                    });
        });

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(BienvenidaActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Acción del botón "Directorio"
        btnDirectorio.setOnClickListener(v -> {
            Intent intent = new Intent(BienvenidaActivity.this, DirectorioActivity.class);
            startActivity(intent);
        });
    }
}
