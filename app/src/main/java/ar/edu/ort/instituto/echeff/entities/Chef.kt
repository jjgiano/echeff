package ar.edu.ort.instituto.echeff.entities

class Chef(
    id: String,
    nombre: String,
    email: String,
    urlFoto: String,
    estado: Int,
    telefono: String
) : Usuario(
    id,
    nombre,
    email,
    urlFoto,
    estado,
    telefono
) {
    constructor() : this("", "", "", "", -1, "")
}