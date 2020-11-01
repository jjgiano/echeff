package ar.edu.ort.instituto.echeff.entities

enum class EstadoReserva(val id: Int){
    NUEVO(1), // el cliente crea una reserva
    PAGADA(2), // el cliente para una de las propuestas que un chef le hizo
    ACONFIRMAR(3), // son las que tiene una propuesta tambien en estado a confirmar, llega a este estado cuando el chef envio una propuesta
    FINALIZADA(4), // el chef termino el trabajo
    MODIFICADA(5), // son las reservas que estan pendientes, el cliente solicitó modificacion en una propuesta
    CANCELADO(6) // el cliente cancela la reserva (aun no hay boton, se hace por mesa de ayuda)
}