package ar.edu.ort.instituto.echeff.entities

import android.os.Parcel
import android.os.Parcelable

class Chef(
    var id: String,
    var nombre: String,
    var email: String,
    var urlFoto: String,
    var urlDiploma: String,
    var estado: Int,
    var telefono: String,
    var idUsuario: String
) : Usuario(), Parcelable
{
    constructor() : this("", "", "", "", "", -1, "", "")
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!
        )

    init {
        this.id = id
        this.nombre = nombre
        this.email = email
        this.estado = estado
        this.telefono = telefono
        this.urlDiploma = urlDiploma
        this.idUsuario = idUsuario
        this.urlFoto = urlFoto
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(urlFoto)
        parcel.writeString(urlDiploma)
        parcel.writeInt(estado)
        parcel.writeString(telefono)
        parcel.writeString(idUsuario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chef> {

        override fun createFromParcel(parcel: Parcel): Chef {
            return Chef(parcel)
        }

        override fun newArray(size: Int): Array<Chef?> {
            return arrayOfNulls(size)
        }
    }
}
