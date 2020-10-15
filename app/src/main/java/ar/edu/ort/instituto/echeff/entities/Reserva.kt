package ar.edu.ort.instituto.echeff.entities

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Reserva(id: String, fecha: String, hora: String, direccion: String, tipoCocina: String, tieneHorno: String,  comensales: Int, tipoServicio: String, estiloCocina: String, notas : String, idUsuario: Int, idEstadoReserva: Int) :
    Parcelable {

    var id: String
    var fecha: String
    var hora: String
    var direccion: String
    var tipoCocina: String
    var tieneHorno: String
    var comensales: Int
    var tipoServicio : String
    var estiloCocina: String
    var notas : String
    var idUsuario: Int
    var idEstadoReserva: Int


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
         parcel.readString()!!,
         parcel.readString()!!,
         parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    init {
        this.id = id
        this.fecha = fecha
        this.hora = hora
        this.direccion = direccion
        this.tipoCocina = tipoCocina
        this.tieneHorno = tieneHorno
        this.comensales = comensales
        this.estiloCocina = estiloCocina
        this.tipoServicio = tipoServicio
        this.notas = notas
        this.idUsuario = idUsuario
        this.idEstadoReserva = idEstadoReserva
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(fecha)
        parcel.writeString(hora)
        parcel.writeString(direccion)
        parcel.writeString(tipoCocina)
        parcel.writeString(tieneHorno)
        parcel.writeInt(comensales)
        parcel.writeString(estiloCocina)
        parcel.writeString(tipoServicio)
        parcel.writeString(notas)
        parcel.writeInt(idUsuario)
        parcel.writeInt(idEstadoReserva)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reserva> {

        override fun createFromParcel(parcel: Parcel): Reserva {
            return Reserva(parcel)
        }

        override fun newArray(size: Int): Array<Reserva?> {
            return arrayOfNulls(size)
        }
    }
}