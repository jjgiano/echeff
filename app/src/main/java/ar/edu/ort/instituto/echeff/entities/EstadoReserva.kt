package ar.edu.ort.instituto.echeff.entities

enum class EstadoReserva(val id: Int){
    NUEVO(0),
    PENDIENTE(1),
    CONFIRMADA(2),
    PAGADA(3),
    ACONFIRMAR(4)
}