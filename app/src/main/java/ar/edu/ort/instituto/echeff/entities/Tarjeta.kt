package ar.edu.ort.instituto.echeff.entities

class Tarjeta(
    idUsuario: String,
    numero: String,
    nombre: String,
    vencimiento: String,
    csv: String
) {

    var idUsuario: String = idUsuario
    var numero: String = numero
    var nombre: String = nombre
    var vencimiento: String = vencimiento
    var csv: String = csv


    constructor() : this("", "", "", "", "")
}