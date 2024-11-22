package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DirectorioActivity : AppCompatActivity() {
    private lateinit var recyclerViewDirectorio: RecyclerView
    private lateinit var directorioAdapter: DirectorioAdapter
    private val doctorList: MutableList<Doctor> = ArrayList()
    private lateinit var btnVolverDirectorio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directorio)

        // Inicializar RecyclerView
        recyclerViewDirectorio = findViewById(R.id.recyclerViewDirectorio)
        recyclerViewDirectorio.layoutManager = LinearLayoutManager(this)
        directorioAdapter = DirectorioAdapter(this, doctorList)
        recyclerViewDirectorio.adapter = directorioAdapter

        // Inicializar Botón Volver
        btnVolverDirectorio = findViewById(R.id.btnVolverDirectorio)
        btnVolverDirectorio.setOnClickListener {
            val intent = Intent(this@DirectorioActivity, BienvenidaActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Cargar datos desde Firestore
        cargarDirectorio()
    }

    private fun cargarDirectorio() {
        val db = FirebaseFirestore.getInstance()
        db.collection("directorio")
            .get()
            .addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                val listaDoctores = queryDocumentSnapshots.toObjects(Doctor::class.java)
                doctorList.clear()
                doctorList.addAll(listaDoctores)
                directorioAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e: Exception ->
                Toast.makeText(this, "Error al cargar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
