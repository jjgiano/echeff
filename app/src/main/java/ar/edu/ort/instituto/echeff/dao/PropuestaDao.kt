package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ar.edu.ort.instituto.echeff.entities.EstadoPropuesta
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

public interface PropuestaDao {


    public suspend fun add(propuesta: Propuesta) {

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef

        try {
            val data = query
                .add(propuesta).addOnSuccessListener { result ->
                    var id = result.id
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
            val data = query.document(propuesta.id).set(propuesta)
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

    suspend fun getPropuestaByChef(idChef : String) : MutableList<Propuesta> {

        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef.whereEqualTo("idChef",idChef)

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
    suspend fun getPropuestaById(id : String) : Propuesta {

        var propuesta: Propuesta = Propuesta()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef.whereEqualTo("id",id)

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
    suspend fun cambiarEstado(propuesta: Propuesta, estado : Int) {
        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef
        try {
            val data = query.document(propuesta.id).update("idEstadoPropuesta",estado)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    /**
     * A partir del ID del cliente obtiene todas las propuestas en estado ACONFIRMAR (2)
     * A partir de cada una de las propuestas obtenidas busca todas las reservas asociadas
     * que esten en estado ACONFIRMAR
     */
    suspend fun getPropuestasAConfirmar(idUsuario: String): MutableList<Propuesta>{
        var propuestaList: MutableList<Propuesta> = ArrayList<Propuesta>()

        val questionRef = Firebase.firestore.collection("propuestas")
        val query = questionRef
            .whereEqualTo("idEstadoPropuesta", EstadoPropuesta.ACONFIRMAR.id)

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
}


