package ar.edu.ort.instituto.echeff.dao

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ar.edu.ort.instituto.echeff.adapters.adapterListReserva
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.math.log


public interface ReservaDao{



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
        try{
            val data = query.document(reserva.id).set(reserva)
        } catch (e: Exception){
            Log.d("Error", e.toString())
        }
    }
    suspend fun getAllReservas(): MutableList<Reserva> {

        var reservaList : MutableList<Reserva> = ArrayList<Reserva>()

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
}


