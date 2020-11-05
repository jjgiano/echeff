package ar.edu.ort.instituto.echeff.entities

class Configuracion(
    uid: String,
    notificaciones: Boolean,
    newsletter: Boolean,
    promociones: Boolean,
    emails: Boolean
) {
    var uid: String
    var notificaciones: Boolean
    var newsletter: Boolean
    var promociones: Boolean
    var emails: Boolean

    constructor() : this("", false, false, false, false)

    init {
        this.uid = uid
        this.notificaciones = notificaciones
        this.newsletter = newsletter
        this.promociones = promociones
        this.emails = emails
    }
}