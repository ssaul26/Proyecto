package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextCorreo, editTextTelefono, editTextContrasena;
    private RadioGroup rgRoles;
    private Button btnRegistrar;

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
        rgRoles = findViewById(R.id.rgRoles);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Acción del botón "Registrar"
        btnRegistrar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();
            String correo = editTextCorreo.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            // Validación de campos
            if (!validarCampos(nombre, correo, telefono, contrasena)) {
                return; // Si la validación falla, no se procede
            }

            // Determinar el rol seleccionado
            int selectedRoleId = rgRoles.getCheckedRadioButtonId();
            if (selectedRoleId == -1) {
                Toast.makeText(this, "Por favor, selecciona un rol.", Toast.LENGTH_SHORT).show();
                return;
            }

            String rol = ((RadioButton) findViewById(selectedRoleId)).getText().toString().toLowerCase();

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
                            userData.put("rol", rol);
                            userData.put("formularioCompletado", false); // Indica si el formulario ha sido completado

                            db.collection("usuarios").document(userId).set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
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
    }

    // Método para validar los campos
    private boolean validarCampos(String nombre, String correo, String telefono, String contrasena) {
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, completa el campo de nombre.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(correo)) {
            Toast.makeText(this, "El correo debe ser válido y terminar en .com, .com.mx o .net.mx.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhone(telefono)) {
            Toast.makeText(this, "Verifique el campo de número (10 dígitos).", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (contrasena.isEmpty() || contrasena.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Si todo es válido, retornamos true
    }

    // Validar correo electrónico con dominios específicos
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.(com|com\\.mx|net\\.mx)$";
        return email.matches(emailPattern);
    }

    // Validar número de teléfono (10 dígitos)
    private boolean isValidPhone(String phone) {
        return phone.matches("^\\d{10}$");
    }
}
