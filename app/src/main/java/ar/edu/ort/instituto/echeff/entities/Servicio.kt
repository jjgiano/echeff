package ar.edu.ort.instituto.echeff.entities

class Servicio(idReserva: Int, idPropuesta: Int) {
    var idReserva: Int
    var idPropuesta: Int

    init {
        this.idReserva = idReserva
        this.idPropuesta = idPropuesta
    }
}