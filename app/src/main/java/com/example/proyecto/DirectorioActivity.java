package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DirectorioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDirectorio;
    private DirectorioAdapter directorioAdapter;
    private List<Doctor> doctorList = new ArrayList<>();
    private Button btnVolverDirectorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directorio);

        // Inicializar RecyclerView
        recyclerViewDirectorio = findViewById(R.id.recyclerViewDirectorio);
        recyclerViewDirectorio.setLayoutManager(new LinearLayoutManager(this));
        directorioAdapter = new DirectorioAdapter(this, doctorList);
        recyclerViewDirectorio.setAdapter(directorioAdapter);

        // Inicializar Botón Volver
        btnVolverDirectorio = findViewById(R.id.btnVolverDirectorio);
        btnVolverDirectorio.setOnClickListener(v -> {
            Intent intent = new Intent(DirectorioActivity.this, BienvenidaActivity.class);
            startActivity(intent);
            finish();
        });

        // Cargar datos desde Firestore
        cargarDirectorio();
    }

    private void cargarDirectorio() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("directorio")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Doctor> listaDoctores = queryDocumentSnapshots.toObjects(Doctor.class);
                    doctorList.clear();
                    doctorList.addAll(listaDoctores);
                    directorioAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}