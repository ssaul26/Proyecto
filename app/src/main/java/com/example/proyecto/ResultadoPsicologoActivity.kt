package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoPsicologoActivity extends AppCompatActivity {

    private TextView textViewCedula, textViewEstudios, textViewEspecialidad, textViewUbicacion;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_psicologo);

        // Referencias a los elementos del layout
        textViewCedula = findViewById(R.id.textViewCedula);
        textViewEstudios = findViewById(R.id.textViewEstudios);
        textViewEspecialidad = findViewById(R.id.textViewEspecialidad);
        textViewUbicacion = findViewById(R.id.textViewUbicacion);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtener los datos del intent
        Intent intent = getIntent();
        String cedula = intent.getStringExtra("cedula");
        String estudios = intent.getStringExtra("estudios");
        String especialidad = intent.getStringExtra("especialidad");
        String ubicacion = intent.getStringExtra("ubicacion");

        // Mostrar los datos en los TextView
        textViewCedula.setText("Cédula: " + (cedula != null ? cedula : "N/A"));
        textViewEstudios.setText("Estudios: " + (estudios != null ? estudios : "N/A"));
        textViewEspecialidad.setText("Especialidad: " + (especialidad != null ? especialidad : "N/A"));
        textViewUbicacion.setText("Ubicación: " + (ubicacion != null ? ubicacion : "N/A"));

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intentCerrarSesion = new Intent(ResultadoPsicologoActivity.this, LoginActivity.class);
            intentCerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentCerrarSesion);
            finish();
        });
    }
}
