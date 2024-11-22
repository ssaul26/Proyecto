package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PsicologoActivity extends AppCompatActivity {

    private EditText cedulaProfesional;
    private Spinner spinnerEstudios, spinnerEspecialidad, spinnerUbicacionPsicologo;
    private Button btnEnviarCuestionario, btnVolverPsicologo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psicologo);

        // Referencias a los elementos del layout
        cedulaProfesional = findViewById(R.id.cedulaProfesional);
        spinnerEstudios = findViewById(R.id.spinnerEstudios);
        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidad);
        spinnerUbicacionPsicologo = findViewById(R.id.spinnerUbicacionPsicologo);
        btnEnviarCuestionario = findViewById(R.id.btnEnviarCuestionario);
        btnVolverPsicologo = findViewById(R.id.btnVolverPsicologo);

        // Configurar Spinner para Estudios Profesionales
        ArrayAdapter<CharSequence> adapterEstudios = ArrayAdapter.createFromResource(this,
                R.array.opciones_estudios, android.R.layout.simple_spinner_item);
        adapterEstudios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstudios.setAdapter(adapterEstudios);

        // Configurar Spinner para Área de Especialidad
        ArrayAdapter<CharSequence> adapterEspecialidad = ArrayAdapter.createFromResource(this,
                R.array.opciones_especialidad, android.R.layout.simple_spinner_item);
        adapterEspecialidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecialidad.setAdapter(adapterEspecialidad);

        // Configurar Spinner para Ubicación
        ArrayAdapter<CharSequence> adapterUbicacion = ArrayAdapter.createFromResource(this,
                R.array.opciones_ubicacion, android.R.layout.simple_spinner_item);
        adapterUbicacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUbicacionPsicologo.setAdapter(adapterUbicacion);

        // Acción del botón Enviar
        btnEnviarCuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del formulario
                String cedula = cedulaProfesional.getText().toString();
                String estudios = spinnerEstudios.getSelectedItem().toString();
                String especialidad = spinnerEspecialidad.getSelectedItem().toString();
                String ubicacion = spinnerUbicacionPsicologo.getSelectedItem().toString();

                // Mostrar datos en un Toast
                Toast.makeText(PsicologoActivity.this,
                        "Cédula: " + cedula +
                                "\nEstudios: " + estudios +
                                "\nEspecialidad: " + especialidad +
                                "\nUbicación: " + ubicacion,
                        Toast.LENGTH_LONG).show();
            }
        });

        // Acción del botón Volver
        btnVolverPsicologo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la pantalla de bienvenida
                Intent intent = new Intent(PsicologoActivity.this, BienvenidaActivity.class);
                startActivity(intent);
            }
        });
    }
}
