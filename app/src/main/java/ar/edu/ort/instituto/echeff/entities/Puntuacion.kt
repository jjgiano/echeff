package ar.edu.ort.instituto.echeff.entities

class Puntuacion(
    var idPuntuacion: Int,
    var mensaje: String = "",
    var idChef: String,
    var idReserva: String
) {
    constructor(): this(0,"","", "")
}