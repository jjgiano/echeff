package ar.edu.ort.instituto.echeff.entities

class Reserva(
    id: Int,
    fecha: String,
    hora: String,
    direccion: String,
    tipoCocina: String,
    comensales: Int,
    estiloCocina: String,
    idUsuario: Int
) {
    var id: Int
    var fecha: String
    var hora: String
    var dieccion: String
    var tipoCocina: String
    var comensales: Int
    var estiloCocina: String
    var idUsuario: Int

    init {
        this.id = id
        this.fecha = fecha
        this.hora = hora
        this.dieccion = direccion
        this.tipoCocina = tipoCocina
        this.comensales = comensales
        this.estiloCocina = estiloCocina
        this.idUsuario = idUsuario
    }
}