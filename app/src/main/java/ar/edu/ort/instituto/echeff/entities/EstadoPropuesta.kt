package ar.edu.ort.instituto.echeff.entities

enum class EstadoPropuesta(val id: Int){
    NUEVO(1),
    ACONFIRMAR(2),
    PAGADO(3),
    MODIFICADO(4),
    RECHAZADO(5),
    FINALIZADO(6)
}