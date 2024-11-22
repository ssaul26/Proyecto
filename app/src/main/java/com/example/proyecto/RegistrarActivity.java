package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextCorreo, editTextTelefono, editTextContrasena;
    private Button btnRegistrar, btnVolver; // Agregado btnVolver

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Referenciar los campos
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver); // Referencia del botón "Volver"

        // Acción del botón "Registrar"
        btnRegistrar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();
            String correo = editTextCorreo.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            // Validación de campos
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registrar usuario en Firebase Authentication
            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            // Guardar datos en Firestore
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("nombre", nombre);
                            userData.put("correo", correo);
                            userData.put("telefono", telefono);

                            db.collection("usuarios").document(userId).set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrarActivity.this, BienvenidaActivity.class);
                                        intent.putExtra("nombre", nombre);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Error al guardar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Error al registrar usuario: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Acción del botón "Volver"
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
