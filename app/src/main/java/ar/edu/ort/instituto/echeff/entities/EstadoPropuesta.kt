package ar.edu.ort.instituto.echeff.entities

enum class EstadoPropuesta(val id: Int){
    NUEVO(1), // el chef crea una propuesta pero aun no lo envio al cliente
    ACONFIRMAR(2), // una vez que el chef envia al cliente la propuesta
    PAGADO(3), // el usuario paga una propuesta
    FINALIZADO(4), // el chef realizo el trabajo
    MODIFICADO(5), // en caso que el cliente solicite modificaciones
    RECHAZADO(6), // en caso que otra propuesta de una reserva haya sido pagada
    DESCARTADO (7), // cuando una de las propuestas de la reserva pasa al estado PAGADO las demas se descartan
    PAUSADO(8) // // cuando una de las propuestas de la reserva pasa al estado MODIFICADO las demas se pausan
}