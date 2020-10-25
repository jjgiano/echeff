package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


public interface ReservaDao {

    public suspend fun add(reserva: Reserva) {

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
        val query = questionRef.whereEqualTo("idEstadoReserva", 1)
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
        val query = questionRef.whereEqualTo("idEstadoReserva", 3)
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
        val query = questionRef.whereEqualTo("idEstadoReserva", 5)
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

    suspend fun getReservasNuevas(idUsuario: Number): MutableList<Reserva> {

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
}


