package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.EstadoServicio
import ar.edu.ort.instituto.echeff.entities.Problema
import ar.edu.ort.instituto.echeff.entities.Servicio
import ar.edu.ort.instituto.echeff.entities.TipoServicio
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface   ServicioDao {

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

    public suspend fun addProblema(problema: Problema) {

        val questionRef = Firebase.firestore.collection("problemas")
        val query = questionRef

        try {
            val data = query
                .add(problema).addOnSuccessListener { result ->
                    var id = result.id
                    problema.id = id
                    query.document(id).set(problema)
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

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }

    suspend fun getServiciosPendientes(): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio",EstadoServicio.ACTIVO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }


    suspend fun getServiciosFinalizados(): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio", EstadoServicio.FINALIZADO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }

    suspend fun getServiciosCancelados(): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef.whereEqualTo("idEstadoServicio", EstadoServicio.CANCELADO.id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
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

    suspend fun getServiciosCanceladosByChef(id: String): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef
                    .whereEqualTo("idEstadoServicio", EstadoServicio.CANCELADO.id)
                    .whereEqualTo("idChef",id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }
    suspend fun getServiciosFinalizadosByChef(id: String): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef
            .whereEqualTo("idEstadoServicio", EstadoServicio.FINALIZADO.id)
            .whereEqualTo("idChef",id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }
    suspend fun getServiciosPendientesByChef(id: String): MutableList<Servicio> {

        var servicioList: MutableList<Servicio> = ArrayList<Servicio>()

        val questionRef = Firebase.firestore.collection("servicios")
        val query = questionRef
            .whereEqualTo("idEstadoServicio", EstadoServicio.ACTIVO.id)
            .whereEqualTo("idChef",id)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                servicioList.add(document.toObject<Servicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return servicioList
    }

    suspend fun getTipoServicios(): MutableList<TipoServicio>{
        var tipoServiciosList: MutableList<TipoServicio> = ArrayList()

        val questionRef = Firebase.firestore.collection("tipoServicio")
        val query = questionRef.orderBy("idTipoServicio")

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                tipoServiciosList.add(document.toObject<TipoServicio>())
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return tipoServiciosList
    }
}