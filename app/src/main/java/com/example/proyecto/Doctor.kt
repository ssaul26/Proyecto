package com.example.proyecto

class Doctor {
    // Getters y setters
    var nombre: String? = null
    var especialidad: String? = null
    var ubicacion: String? = null
    var imagen: String? = null

    // Constructor vacío (requerido por Firebase)
    constructor()

    // Constructor con argumentos
    constructor(nombre: String?, especialidad: String?, ubicacion: String?, imagen: String?) {
        this.nombre = nombre
        this.especialidad = especialidad
        this.ubicacion = ubicacion
        this.imagen = imagen
    }
}
