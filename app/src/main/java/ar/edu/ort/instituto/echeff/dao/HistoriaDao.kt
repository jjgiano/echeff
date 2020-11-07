package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface HistoriaDao {
    suspend fun add(historia: Historia) {

        val questionRef = Firebase.firestore.collection("historiasChef")
        val query = questionRef

        try {
            query
                .add(historia).addOnSuccessListener { result ->
                    var id = result.id
                    historia.id = id
                    query.document(id).set(historia)
                }
                .await()
        } catch (e: Exception) {
        }
    }

    suspend fun update(historia: Historia) {
        val questionRef = Firebase.firestore.collection("historiasChef")
        val query = questionRef
        try {
           query.document(historia.id).set(historia)
        } catch (e: Exception) {

        }
    }

    suspend fun getHistorasByChef(idChef:String) : MutableList<Historia> {
        var listaHistorias: MutableList<Historia> = ArrayList<Historia>()

        val questionRef = Firebase.firestore.collection("historiasChef")
        val query = questionRef.whereEqualTo("idChef", idChef)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                listaHistorias.add(document.toObject<Historia>())
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return listaHistorias
    }
}