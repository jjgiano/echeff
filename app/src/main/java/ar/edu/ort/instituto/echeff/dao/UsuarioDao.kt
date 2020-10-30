package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Cliente
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface UsuarioDao {

    suspend fun getReservaById(id : String) : Cliente {

        var cliente: Cliente = Cliente()

        val questionRef = Firebase.firestore.collection("usuarios")
        val query = questionRef.whereEqualTo("id",id)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                cliente = document.toObject<Cliente>()
            }


        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return cliente
    }
}