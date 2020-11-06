package ar.edu.ort.instituto.echeff.entities

class Chef(
    var id: String,
    var nombre: String,
    var email: String,
    var urlFoto: String,
    var urlDiploma: String,
    var estado: Int,
    var telefono: String,
    var idUsuario: String
) : Usuario()
{
    constructor() : this("", "", "", "", "", -1, "", "")
}