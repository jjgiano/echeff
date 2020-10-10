package ar.edu.ort.instituto.echeff.entities

enum class EstadoPropuesta(val id: Int){
    ACTIVO(1),
    FINALIZADO(2),
    CANCELADO(3),
    CONFIRMADO(4),
    MODIFICADO(5)
}