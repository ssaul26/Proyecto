package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BienvenidaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button btnPaciente, btnPsicologo, btnCerrarSesion;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Referencias a los botones
        btnPaciente = findViewById(R.id.btnSoyPaciente);
        btnPsicologo = findViewById(R.id.btnSoyPsicologo);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtener el ID del usuario actual
        userId = mAuth.getCurrentUser().getUid();

        // Consultar en Firestore si el formulario ya está contestado
        db.collection("usuarios").document(userId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Boolean formularioCompletado = document.getBoolean("formularioCompletado");

                        if (formularioCompletado != null && formularioCompletado) {
                            // Mostrar mensaje de formulario contestado
                            findViewById(R.id.textViewFormularioEstado).setVisibility(View.VISIBLE);

                            // Deshabilitar botones
                            btnPaciente.setEnabled(false);
                            btnPsicologo.setEnabled(false);
                        } else {
                            // Configurar botones si no se ha completado el formulario
                            btnPaciente.setOnClickListener(v -> {
                                Intent pacienteIntent = new Intent(BienvenidaActivity.this, PacienteActivity.class);
                                startActivity(pacienteIntent);
                            });

                            btnPsicologo.setOnClickListener(v -> {
                                Intent psicologoIntent = new Intent(BienvenidaActivity.this, PsicologoActivity.class);
                                startActivity(psicologoIntent);
                            });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al verificar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener(v -> {
            mAuth.signOut(); // Cerrar sesión en Firebase
            Intent intent = new Intent(BienvenidaActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
