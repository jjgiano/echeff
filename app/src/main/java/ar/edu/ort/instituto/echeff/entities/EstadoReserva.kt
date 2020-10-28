package ar.edu.ort.instituto.echeff.entities

enum class EstadoReserva(val id: Int){
    NUEVO(1),
    PAGADA(2),
    ACONFIRMAR(3),
    FINALIZADA(4),
    MODIFICADA(5),
    CANCELADO(6)
}