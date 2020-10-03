package ar.edu.ort.instituto.echeff.entities

class Chef(
    id: Number, nombre: String, email: String, clave: String, urlFoto: String,
    estado: String
) : Usuario(
    id, nombre, email,
    clave,
    urlFoto, estado
) {

}