package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Servicio
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface ServicioDao {

    public suspend fun add(servicio: Servicio) {

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef

        try {
            val data = query
                .add(servicio).addOnSuccessListener { result ->
                    var id = result.id
                    servicio.id = id
                    query.document(id).set(servicio)
                }
                .await()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun update(servicio: Servicio) {
        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef
        try {
            val data = query.document(servicio.id).set(servicio)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    suspend fun getAllServicios(): MutableList<Servicio> {

        var reservaList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getServiciosPendientes(): MutableList<Servicio> {

        var reservaList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio", 1)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }


    suspend fun getServiciosFinalizados(): MutableList<Servicio> {

        var reservaList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio", 2)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    suspend fun getServiciosCancelados(): MutableList<Servicio> {

        var reservaList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio", 3)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                reservaList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return reservaList
    }

    //MODIFICAR ESTADO DE Servicios
    suspend fun cambiarEstado(servicio: Servicio, estado : Int) {
        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef
        try {
            val data = query.document(servicio.id).update("idEstadoServicio",estado)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }
}