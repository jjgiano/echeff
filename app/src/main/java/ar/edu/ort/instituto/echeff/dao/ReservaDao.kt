package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Tarjeta
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


interface ReservaDao {

     suspend fun add(reserva: Reserva) {

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef

        try {
            val data = query
                .add(reserva).addOnSuccessListener { result ->
                    var id = result.id
                    reserva.id = id
                    query.document(id).set(reserva)
                }
                .await()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun update(reserva: Reserva) {
        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
        try {
            val data = query.document(reserva.id).set(reserva)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun getAllReservas(): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasDisponibles(): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef.whereEqualTo("idEstadoReserva", EstadoReserva.NUEVO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasAConfirmar(): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef.whereEqualTo("idEstadoReserva", EstadoReserva.ACONFIRMAR.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasAModificar(): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef.whereEqualTo("idEstadoReserva", EstadoReserva.MODIFICADA.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservaById(id : String) : Reserva {

        var reserva: Reserva = Reserva()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef.whereEqualTo("id",id)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reserva = document.toObject<Reserva>()
            }


        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reserva
    }

    //MODIFICAR ESTADO DE RESERVA
    suspend fun cambiarEstado(reserva: Reserva, estado : Int) {
        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
        try {
            val data = query.document(reserva.id).update("idEstadoReserva",estado)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun getReservasNuevas(idUsuario: String): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.NUEVO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasAConfirmar(idUsuario: String): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.ACONFIRMAR.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }
    suspend fun getReservasFinalizadas(idUsuario: String): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.FINALIZADA.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasPagadas(idUsuario: String): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.PAGADA.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getReservasPendientes(idUsuario: String): MutableList<Reserva> {

        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val questionRef = Firebase.firestore.collection("reservas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.MODIFICADA.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun setTarjetaDeCredito(tarjeta: Tarjeta){
        val questionRef = Firebase.firestore.collection("tarjetas")
        val query = questionRef
        try {
            val data = query
                .add(tarjeta)
                .await()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun getTarjetaUsuario(idUsuario: String):Tarjeta{
        var tarjetaABuscar = Tarjeta()

        val questionRef = Firebase.firestore.collection("tarjetas")
        val query = questionRef
            .whereEqualTo("idUsuario", idUsuario)
            .limit(1)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                tarjetaABuscar = document.toObject<Tarjeta>()
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return tarjetaABuscar
    }
}


