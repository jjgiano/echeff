package ar.edu.ort.instituto.echeff.entities

class Problema(id: String, opcion: String, comentarios: String, idUsuario: String, estado: Number) {

    var id: String
    var opcion: String
    var comentarios: String
    var idUsuario: String
    var estado: Number


    constructor() : this("", "", "", "", 1)

    init {
        this.id = id
        this.opcion = opcion
        this.comentarios = comentarios
        this.idUsuario = idUsuario
        this.estado = estado
    }
}