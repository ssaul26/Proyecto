package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BienvenidaActivity extends AppCompatActivity {

    private String nombre; // Variable para guardar el nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        // Recibir el nombre desde RegistrarActivity
        nombre = getIntent().getStringExtra("nombre");

        // Referencia a los botones
        Button btnPaciente = findViewById(R.id.btnSoyPaciente);
        Button btnPsicologo = findViewById(R.id.btnSoyPsicologo);

        // Acción para el botón "Soy Paciente"
        btnPaciente.setOnClickListener(v -> {
            Intent pacienteIntent = new Intent(BienvenidaActivity.this, PacienteActivity.class);
            pacienteIntent.putExtra("nombre", nombre); // Pasar el nombre
            startActivity(pacienteIntent);
        });

        // Acción para el botón "Soy Psicólogo"
        btnPsicologo.setOnClickListener(v -> {
            Intent psicologoIntent = new Intent(BienvenidaActivity.this, PsicologoActivity.class);
            psicologoIntent.putExtra("nombre", nombre); // Pasar el nombre
            startActivity(psicologoIntent);
        });
    }
}
