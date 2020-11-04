package ar.edu.ort.instituto.echeff.entities

class Cliente(
    id: String,
    nombre: String,
    email: String,
    urlFoto: String,
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
    constructor() : this("", "", "", "", 0, "", "")
}