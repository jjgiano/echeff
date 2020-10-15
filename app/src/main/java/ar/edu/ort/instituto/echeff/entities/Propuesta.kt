package ar.edu.ort.instituto.echeff.entities

class Propuesta(
    id: Int,
    snack: String,
    entrada: String,
    plato: String,
    postre: String,
    adicional: String,
    total: Double,
    idChef: Int,
    idReserva: String,
    urlImg: String? = null
) {
    var id: Int
    var snack: String
    var entrada: String
    var plato: String
    var postre: String
    var adicional: String
    var total: Double
    var idChef: Int
    var idReserva: String
    var urlImg: String?

    init {
        this.id = id
        this.snack = snack
        this.entrada = entrada
        this.plato = plato
        this.postre = postre
        this.adicional = adicional
        this.total = total
        this.idChef = idChef
        this.idReserva = idReserva
        this.urlImg = urlImg
    }

}