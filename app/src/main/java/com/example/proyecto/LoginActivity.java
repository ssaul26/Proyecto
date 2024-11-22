package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCorreo, editTextContrasena;
    private Button btnIniciarSesion;
    private TextView lblRegistrar; // Agrega esta referencia

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Referencias a los campos del layout
        editTextCorreo = findViewById(R.id.txtUsuario);
        editTextContrasena = findViewById(R.id.txtContraseña);
        btnIniciarSesion = findViewById(R.id.btnIngresar);
        lblRegistrar = findViewById(R.id.lblRegistrar); // Referencia al enlace "Regístrate"

        // Acción para "Regístrate"
        lblRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
            startActivity(intent); // Redirige a la pantalla de registro
        });

        // Acción del botón "Iniciar sesión"
        btnIniciarSesion.setOnClickListener(v -> {
            String correo = editTextCorreo.getText().toString();
            String contrasena = editTextContrasena.getText().toString();

            // Validación de campos vacíos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Autenticar usuario con Firebase Authentication
            mAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Obtener datos del usuario desde Firestore
                            String userId = mAuth.getCurrentUser().getUid();
                            db.collection("usuarios").document(userId).get()
                                    .addOnSuccessListener(document -> {
                                        if (document.exists()) {
                                            String nombre = document.getString("nombre");
                                            Intent intent = new Intent(LoginActivity.this, BienvenidaActivity.class);
                                            intent.putExtra("nombre", nombre);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Usuario no encontrado en Firestore.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
