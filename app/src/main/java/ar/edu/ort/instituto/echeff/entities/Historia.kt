package ar.edu.ort.instituto.echeff.entities

class Historia(id: String, urlImagen: String, comentario: String, cantidadMegusta: Int) {

    var id: String = id
    var urlImagen: String = urlImagen
    var comentario: String = comentario
    var cantidadMegusta: Int = cantidadMegusta


    init {
        this.id = id
        this.urlImagen = urlImagen
        this.comentario = comentario
        this.cantidadMegusta = cantidadMegusta
    }
}