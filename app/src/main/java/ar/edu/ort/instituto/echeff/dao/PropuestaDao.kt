package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.EstadoPropuesta
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface PropuestaDao {


    suspend fun add(propuesta: Propuesta) {

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef

        try {
            query
                .add(propuesta).addOnSuccessListener { result ->
                    val id = result.id
                    propuesta.id = id
                    query.document(id).set(propuesta)
                }
                .await()
        } catch (e: Exception) {
        }
    }

    suspend fun update(propuesta: Propuesta) {
        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef
        try {
            query.document(propuesta.id).set(propuesta)
        } catch (e: Exception) {

        }
    }

    suspend fun getAll(): MutableList<Propuesta> {

        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                propuestaList.add(document.toObject<Propuesta>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return propuestaList
    }

    suspend fun getPropuestaByChef(idChef: String): MutableList<Propuesta> {

        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef.whereEqualTo("idChef", idChef)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                propuestaList.add(document.toObject<Propuesta>())
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return propuestaList
    }

    suspend fun getPropuestaById(id: String): Propuesta {

        var propuesta: Propuesta = Propuesta()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef.whereEqualTo("id", id)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                propuesta = document.toObject<Propuesta>()
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

        return propuesta
    }

    suspend fun getPropuestasDestacadas(): MutableList<Propuesta> {

        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef
            .whereEqualTo("destacada", true)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                propuestaList.add(document.toObject<Propuesta>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return propuestaList
    }

    //MODIFICAR ESTADO DE Propuesta
    suspend fun cambiarEstado(propuesta: Propuesta, estado: Int) {
        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef
        try {
            query.document(propuesta.id).update("idEstadoPropuesta", estado)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    /**
     * A partir del ID del cliente obtiene todas las reservas en estado ACONFIRMAR (3)
     * A partir de cada reserva obtenidas acumula las propuestas asociadas en estado ACONFIRMAR (2)
     */
    suspend fun getPropuestasAConfirmar(idUsuario: String): MutableList<Propuesta>{
        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()
        var reservaList: MutableList<Reserva> = ArrayList<Reserva>()

        val reservaRef = Firebase.firestore.collection("reservas")
        val reservaQuery = reservaRef
            .whereEqualTo("idUsuario", idUsuario)
            .whereEqualTo("idEstadoReserva", EstadoReserva.ACONFIRMAR.id)
        try {
            val data = reservaQuery.get().await()
            for (document in data) {
                reservaList.add(document.toObject<Reserva>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

        for(reserva in reservaList) {
            val questionRef = Firebase.firestore.collection("propuestas")
            val query = questionRef
                .whereEqualTo("idEstadoPropuesta", EstadoPropuesta.ACONFIRMAR.id)
                .whereEqualTo("idReserva", reserva.id)
            try {
                val data = query.get().await()
                for (document in data) {
                    propuestaList.add(document.toObject<Propuesta>())
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }

        return propuestaList
    }

    /**
     * La reserva enviada por parametro tambien debe ser una reserva en estado EstadoReserva.FINALIZADO
     */
    suspend fun getPropuestaByReservaFinalizada(idReservaFinalizada: String): Propuesta {
        var propuesta: Propuesta = Propuesta()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef.whereEqualTo("idReserva", idReservaFinalizada)
            .whereEqualTo("idEstadoPropuesta", EstadoPropuesta.FINALIZADO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                propuesta = document.toObject<Propuesta>()
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

        return propuesta
    }
}


