package ar.edu.ort.instituto.echeff.entities

import android.os.Parcel
import android.os.Parcelable

class Historia(id: String, idChef: String, urlImagen: String, comentario: String, cantidadMegusta: Int):
    Parcelable {

    var id: String = id
    var idChef: String = idChef
    var urlImagen: String = urlImagen
    var comentario: String = comentario
    var cantidadMegusta: Int = cantidadMegusta

    constructor(parcel: Parcel) : this(

    ) {
        id = parcel.readString()!!
        idChef = parcel.readString()!!
        urlImagen = parcel.readString()!!
        comentario = parcel.readString()!!
        cantidadMegusta = parcel.readInt()!!
    }

    constructor():this("","","","",0)
    init {
        this.id = id
        this.idChef = idChef
        this.urlImagen = urlImagen
        this.comentario = comentario
        this.cantidadMegusta = cantidadMegusta
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(idChef)
        parcel.writeString(urlImagen)
        parcel.writeString(comentario)
        parcel.writeInt(cantidadMegusta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Historia> {
        override fun createFromParcel(parcel: Parcel): Historia {
            return Historia(parcel)
        }

        override fun newArray(size: Int): Array<Historia?> {
            return arrayOfNulls(size)
        }
    }
}