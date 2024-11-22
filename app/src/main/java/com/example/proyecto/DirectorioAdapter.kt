package com.example.proyecto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto.DirectorioAdapter.DoctorViewHolder

class DirectorioAdapter(private val context: Context, private val doctorList: List<Doctor>) :
    RecyclerView.Adapter<DoctorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]

        holder.textViewNombre.text = doctor.nombre
        holder.textViewEspecialidad.text = doctor.especialidad
        holder.textViewUbicacion.text = doctor.ubicacion

        Glide.with(context)
            .load(doctor.imagen)
            .into(holder.imageViewDoctor)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        var textViewEspecialidad: TextView = itemView.findViewById(R.id.textViewEspecialidad)
        var textViewUbicacion: TextView = itemView.findViewById(R.id.textViewUbicacion)
        var imageViewDoctor: ImageView = itemView.findViewById(R.id.imageViewDoctor)
    }
}
