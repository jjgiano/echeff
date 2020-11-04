package ar.edu.ort.instituto.echeff.entities

class Configuracion(
    idUsuario: String,
    notificaciones: String,
    newsletter: String,
    promociones: String,
    emails: String
) {

    var idUsuario: String
    var notificaciones: String
    var newsletter: String
    var promociones: String
    var emails: String

    constructor() : this("0", "", "", "", "")

    init {
        this.idUsuario = idUsuario
        this.notificaciones = notificaciones
        this.newsletter = newsletter
        this.promociones = promociones
        this.emails = emails
    }
}