package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DirectorioAdapter extends RecyclerView.Adapter<DirectorioAdapter.DoctorViewHolder> {

    private Context context;
    private List<Doctor> doctorList;

    public DirectorioAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.textViewNombre.setText(doctor.getNombre());
        holder.textViewEspecialidad.setText(doctor.getEspecialidad());
        holder.textViewUbicacion.setText(doctor.getUbicacion());

        Glide.with(context)
                .load(doctor.getImagen())
                .into(holder.imageViewDoctor);
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre, textViewEspecialidad, textViewUbicacion;
        ImageView imageViewDoctor;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewEspecialidad = itemView.findViewById(R.id.textViewEspecialidad);
            textViewUbicacion = itemView.findViewById(R.id.textViewUbicacion);
            imageViewDoctor = itemView.findViewById(R.id.imageViewDoctor);
        }
    }
}
