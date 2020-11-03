package ar.edu.ort.instituto.echeff.entities



class Propuesta(
    id: String,
    snack: String,
    entrada: String,
    plato: String,
    postre: String,
    adicional: String,
    total: Double,
    idChef: String,
    idReserva: String,
    urlImg: String? = null,
    destacada: Boolean = false,
    idEstadoPropuesta: Int,
    modificaciones: String,
    importeTotal: Double
) {
    var id: String
    var snack: String
    var entrada: String
    var plato: String
    var postre: String
    var adicional: String
    var total: Double
    var idChef: String
    var idReserva: String
    var urlImg: String?
    var destacada: Boolean?
    var idEstadoPropuesta: Int
    var modificaciones: String
    var importeTotal: Double? = 0.0

    constructor() : this("","","","","","",0.0,"","","", false,1,"",0.0)

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
        this.destacada = destacada
        this.idEstadoPropuesta = idEstadoPropuesta
        this.modificaciones = modificaciones
        this.importeTotal = importeTotal
    }


}