package ar.edu.ort.instituto.echeff.entities

import android.os.Parcel
import android.os.Parcelable

class Servicio(id: String, idReserva: String, idPropuesta: String, idEstadoServicio:Int, idChef: String, idCliente: String) : Parcelable {
    var idReserva: String
    var idPropuesta: String
    var id : String
    var idEstadoServicio : Int
    var idChef : String
    var idCliente : String

    constructor(parcel: Parcel) : this(

    ) {
        idReserva = parcel.readString()!!
        idPropuesta = parcel.readString()!!
        id = parcel.readString()!!
        idEstadoServicio = parcel.readInt()
        idChef = parcel.readString()!!
        idCliente = parcel.readString()!!
    }

    constructor() : this("","","",1, "","")

    init {
        this.id = id
        this.idReserva = idReserva
        this.idPropuesta = idPropuesta
        this.idEstadoServicio = idEstadoServicio
        this.idChef = idChef
        this.idCliente = idCliente
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idReserva)
        parcel.writeString(idPropuesta)
        parcel.writeString(id)
        parcel.writeInt(idEstadoServicio)
        parcel.writeString(idChef)
        parcel.writeString(idCliente)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Servicio> {
        override fun createFromParcel(parcel: Parcel): Servicio {
            return Servicio(parcel)
        }

        override fun newArray(size: Int): Array<Servicio?> {
            return arrayOfNulls(size)
        }
    }
}