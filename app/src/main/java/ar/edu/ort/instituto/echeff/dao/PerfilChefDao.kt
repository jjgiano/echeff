package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface PerfilChefDao {

    suspend fun add(perfil: PerfilChef) {

        val questionRef = Firebase.firestore.collection("perfilesChef")
        val query = questionRef

        try {
            query
                .add(perfil).addOnSuccessListener { result ->
                    val id = result.id
                    perfil.id = id
                    query.document(id).set(perfil)
                }
                .await()
        } catch (e: Exception) {
        }
    }

    suspend fun update(perfil: PerfilChef) {
        val questionRef = Firebase.firestore.collection("perfilesChef")
        val query = questionRef
        try {
           query.document(perfil.id).set(perfil)
        } catch (e: Exception) {

        }
    }

    suspend fun getPerfilByIdChef(id: String): PerfilChef {
        var perfil: PerfilChef = PerfilChef()

        val questionRef = Firebase.firestore.collection("perfilesChef")
        val query = questionRef.whereEqualTo("idChef", id)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                perfil = (document.toObject<PerfilChef>())
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return perfil
    }

}