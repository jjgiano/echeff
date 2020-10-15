package ar.edu.ort.instituto.echeff.dao

import android.content.ContentValues
import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Propuesta
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


public interface propuestasDao{



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
        try{
            val data = query.document(propuesta.id).set(propuesta)
        } catch (e: Exception){

        }
    }
    suspend fun getAll(): MutableList<Propuesta> {

        var propuestaList : MutableList<Propuesta> = ArrayList<Propuesta>()

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
        }
        return propuestaList
    }
}


