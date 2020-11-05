package ar.edu.ort.instituto.echeff.entities

class Historia(id: String, idChef: String, urlImagen: String, comentario: String, cantidadMegusta: Int) {

    var id: String = id
    var idChef: String = idChef
    var urlImagen: String = urlImagen
    var comentario: String = comentario
    var cantidadMegusta: Int = cantidadMegusta

    constructor():this("","","","",0)
    init {
        this.id = id
        this.idChef = idChef
        this.urlImagen = urlImagen
        this.comentario = comentario
        this.cantidadMegusta = cantidadMegusta
    }
}