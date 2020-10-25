package ar.edu.ort.instituto.echeff.entities

class PerfilChef(id: String, idChef: String, bio: String, meGusta:Int) {
    var idChef: String
    var bio: String
    var id: String
    var meGusta: Int

    constructor() : this("", "", "", 1)

    init {
        this.id = id
        this.idChef = idChef
        this.bio = bio
        this.meGusta= meGusta
    }
}