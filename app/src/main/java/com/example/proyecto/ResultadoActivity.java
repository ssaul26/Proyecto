package com.example.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    private TextView textViewLink, textViewBienvenida;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Referencias a los elementos del layout
        textViewLink = findViewById(R.id.textViewLink);
        textViewBienvenida = findViewById(R.id.textViewBienvenida);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtener diagnóstico y nombre desde el intent
        Intent intent = getIntent();
        String diagnostico = intent.getStringExtra("diagnostico");
        String nombre = intent.getStringExtra("nombre");

        // Mostrar el nombre en el mensaje de bienvenida
        textViewBienvenida.setText("¡Bienvenido a MindEase, " + nombre + "!");

        // Determinar el enlace al video según el diagnóstico
        String videoUrl = obtenerVideoUrl(diagnostico);

        // Configurar el enlace al video
        if (!videoUrl.isEmpty()) {
            textViewLink.setText("Ver video relacionado");
            textViewLink.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                startActivity(browserIntent);
            });
        } else {
            textViewLink.setText("No se encontró un video relacionado.");
        }

        // Acción del botón "Cerrar sesión"
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intentCerrarSesion = new Intent(ResultadoActivity.this, LoginActivity.class);
            intentCerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentCerrarSesion);
            finish();
        });
    }

    private String obtenerVideoUrl(String diagnostico) {
        if (diagnostico.contains("Trauma") || diagnostico.contains("Depresión") || diagnostico.contains("Ansiedad")) {
            return "https://www.youtube.com/watch?v=B5rhpspkyWw";
        } else if (diagnostico.contains("Estrés Deportivo") || diagnostico.contains("Rehabilitación Emocional") || diagnostico.contains("Trabajo en Equipo")) {
            return "https://www.youtube.com/watch?v=sowG4gyq-cs";
        } else if (diagnostico.contains("Ludopatía") || diagnostico.contains("Alcoholismo") || diagnostico.contains("Drogadicción")) {
            return "https://www.youtube.com/watch?v=qd9lmvDkJOQ";
        } else if (diagnostico.contains("Problemas de Aprendizaje") || diagnostico.contains("Orientación Vocacional") || diagnostico.contains("Intervención en Disciplina")) {
            return "https://www.youtube.com/watch?v=im3vc2NzCVc";
        } else if (diagnostico.contains("Trastorno del Desarrollo") || diagnostico.contains("Problemas de Conducta") || diagnostico.contains("Manejo del Bullying")) {
            return "https://www.youtube.com/watch?v=FZc7lUl2BIc";
        } else if (diagnostico.contains("Conflicto de Pareja") || diagnostico.contains("Terapia Sexual") || diagnostico.contains("Dinámica Familiar") || diagnostico.contains("Comunicación en Pareja")) {
            return "https://www.youtube.com/watch?v=k-B_sOd9_ng";
        }
        return "";
    }
}
