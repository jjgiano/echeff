package ar.edu.ort.instituto.echeff.entities

class Cliente(
    var id: String,
    var nombre: String,
    var email: String,
    var urlFoto: String,
    var estado: Int,
    var telefono: String,
    var idUsuario: String
) : Usuario() {
    constructor() : this("", "", "", "", 0, "", "")
}