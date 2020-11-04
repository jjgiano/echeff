package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Propuesta
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface UsuarioDao {

    suspend fun getClienteById(id : String) : Cliente {

        var cliente: Cliente = Cliente()

        val questionRef = Firebase.firestore.collection("clientes")
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

    suspend fun getClienteByUserId(userId : String) : Cliente {

        var cliente: Cliente = Cliente()

        val questionRef = Firebase.firestore.collection("clientes")
        val query = questionRef.whereEqualTo("idUsuario", userId)

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

    suspend fun getChefById(id : String) : Chef {

        var chef: Chef = Chef()

        val questionRef = Firebase.firestore.collection("chefs")
        val query = questionRef.whereEqualTo("id",id)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                chef = document.toObject<Chef>()
            }


        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return chef
    }

    suspend fun getChefByUserId(userId : String) : Chef {

        var chef: Chef = Chef()

        val questionRef = Firebase.firestore.collection("chefs")
        val query = questionRef.whereEqualTo("idUsuario", userId)

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                chef = document.toObject<Chef>()
            }


        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return chef
    }



    suspend fun addChef(chef: Chef): Chef {

        val questionRef = Firebase.firestore.collection("chefs")
        val query = questionRef

        try {
            val data = query
                .add(chef).addOnSuccessListener { result ->
                    var id = result.id
                    chef.id = id
                    query.document(id).set(chef)
                }
                .await()
            return chef
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addCliente(cliente: Cliente): Cliente {

        val questionRef = Firebase.firestore.collection("clientes")
        val query = questionRef

        try {
            val data = query
                .add(cliente).addOnSuccessListener { result ->
                    var id = result.id
                    cliente.id = id
                    query.document(id).set(cliente)
                }
                .await()
            return cliente
        } catch (e: Exception) {
            throw e
        }
    }

}