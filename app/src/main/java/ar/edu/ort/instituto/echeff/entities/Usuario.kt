package ar.edu.ort.instituto.echeff.entities

abstract class Usuario(
    id: String,
    nombre: String,
    email: String,
    urlFoto: String,
    estado: String,
    telefono: String
) {
    var id: String
    var nombre: String
    var email: String
    var urlFoto: String
    var estado: String
    var telefono: String

    init {
        this.id = id
        this.nombre = nombre
        this.email = email
        this.urlFoto = urlFoto
        this.estado = estado
        this.telefono = telefono
    }

}