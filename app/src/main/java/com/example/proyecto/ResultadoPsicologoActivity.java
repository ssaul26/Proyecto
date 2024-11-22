package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResultadoPsicologoActivity extends AppCompatActivity {

    private TextView textViewCedula, textViewEstudios, textViewEspecialidad, textViewUbicacion, textViewMensaje;
    private Button btnCerrarSesion;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_psicologo);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Referencias a los elementos del layout
        textViewCedula = findViewById(R.id.textViewCedula);
        textViewEstudios = findViewById(R.id.textViewEstudios);
        textViewEspecialidad = findViewById(R.id.textViewEspecialidad);
        textViewUbicacion = findViewById(R.id.textViewUbicacion);
        textViewMensaje = findViewById(R.id.textViewMensaje);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtener datos del intent
        String cedula = getIntent().getStringExtra("cedula");
        String estudios = getIntent().getStringExtra("estudios");
        String especialidad = getIntent().getStringExtra("especialidad");
        String ubicacion = getIntent().getStringExtra("ubicacion");

        // Mostrar mensaje personalizado
        textViewMensaje.setText("Procesaremos tu solicitud con los siguientes datos:");

        // Mostrar los datos en los TextViews
        textViewCedula.setText("Cédula: " + cedula);
        textViewEstudios.setText("Estudios: " + estudios);
        textViewEspecialidad.setText("Especialidad: " + especialidad);
        textViewUbicacion.setText("Ubicación: " + ubicacion);

        // Acción del botón Cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> {
            // Cerrar sesión de FirebaseAuth
            mAuth.signOut();

            // Redirigir al LoginActivity
            Intent intent = new Intent(ResultadoPsicologoActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
