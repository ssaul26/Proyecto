package com.example.proyecto;

public class Doctor {
    private String nombre;
    private String especialidad;
    private String ubicacion;
    private String imagen;

    // Constructor vacío (requerido por Firebase)
    public Doctor() {
    }

    // Constructor con argumentos
    public Doctor(String nombre, String especialidad, String ubicacion, String imagen) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
