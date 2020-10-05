package ar.edu.ort.instituto.echeff.entities;

class Comentario(id: Int, comentario: String, califiacion: Int, urlAvatar: String) {
    var id: Int = id
    var comentario: String = comentario
    var califiacion: Int = califiacion
    var urlAvatar: String = urlAvatar

    init {
        this.id = id
        this.comentario = comentario
        this.califiacion = califiacion
        this.urlAvatar = urlAvatar
    }
}
