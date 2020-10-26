package ar.edu.ort.instituto.echeff.entities

class TipoServicio(
    nombre: String = "",
    idTipoServicio: String = "",
    precioMinimo: Double = 0.0,
    precioMaximo: Double = 0.0,
    estado: Boolean = false
) {
    var nombre: String
    var idTipoServicio: String
    var precioMinimo: Double
    var precioMaximo: Double
    var estado: Boolean

    init {
        this.nombre = nombre
        this.idTipoServicio = idTipoServicio
        this.precioMaximo = precioMaximo
        this.precioMinimo = precioMinimo
        this.estado = estado
    }

    override fun toString(): String {
        return nombre
    }
}