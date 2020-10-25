package ar.edu.ort.instituto.echeff.entities

class Servicio(id: String, idReserva: String, idPropuesta: String, idEstadoServicio:Int) {
    var idReserva: String
    var idPropuesta: String
    var id : String
    var idEstadoServicio : Int

    constructor() : this("","","",1)

    init {
        this.id = id
        this.idReserva = idReserva
        this.idPropuesta = idPropuesta
        this.idEstadoServicio = idEstadoServicio
    }
}