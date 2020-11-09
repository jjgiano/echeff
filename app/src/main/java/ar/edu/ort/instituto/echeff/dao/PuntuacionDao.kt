package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface PuntuacionDao {

    suspend fun getPuntuacionByChef(idChef:String) : MutableList<Puntuacion> {
        var listaPuntuacion: MutableList<Puntuacion> = ArrayList<Puntuacion>()

        val questionRef = Firebase.firestore.collection("puntuacion")
        val query = questionRef.whereEqualTo("idChef", idChef)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                listaPuntuacion.add(document.toObject<Puntuacion>())
            }

        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return listaPuntuacion
    }
}