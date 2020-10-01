package ar.edu.ort.instituto.echeff.entities

abstract class Usuario(
    id: Number,
    nombre: String,
    email: String,
    clave: String,
    urlFoto: String,
    estado: String
) {
    var id: Number
    var nombre: String
    var email: String
    var clave: String
    var urlFoto: String
    var estado: String

    init {
        this.id = id
        this.nombre = nombre
        this.email = email
        this.clave = clave
        this.urlFoto = urlFoto
        this.estado = estado
    }

}