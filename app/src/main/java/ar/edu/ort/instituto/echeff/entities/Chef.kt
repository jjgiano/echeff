package ar.edu.ort.instituto.echeff.entities

class Chef(
    id: String,
    nombre: String,
    email: String,
    urlFoto: String,
    urlDiploma: String,
    estado: Int,
    telefono: String,
    idUsuario: String
) : Usuario(
    id,
    nombre,
    email,
    urlFoto,
    estado,
    telefono,
    idUsuario
) {
    constructor() : this("", "", "", "", "", -1, "", "")
}