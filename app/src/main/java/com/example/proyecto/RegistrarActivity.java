package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextCorreo, editTextTelefono, editTextContrasena;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Referenciar los campos
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Acción del botón "Registrar"
        btnRegistrar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String correo = editTextCorreo.getText().toString();
            String telefono = editTextTelefono.getText().toString();
            String contrasena = editTextContrasena.getText().toString();

            // Validación de campos
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(RegistrarActivity.this,
                        "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Pasar nombre a BienvenidaActivity
                Intent intent = new Intent(RegistrarActivity.this, BienvenidaActivity.class);
                intent.putExtra("nombre", nombre); // Guardar el nombre
                startActivity(intent);
                finish(); // Finalizar RegistrarActivity
            }
        });

    }
}
