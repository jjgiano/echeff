package ar.edu.ort.instituto.echeff.entities

class Chef(
    id: String, nombre: String, email: String, urlFoto: String,
    estado: String, telefono: String
) : Usuario(
    id, nombre, email,
    urlFoto, estado, telefono
) {

}