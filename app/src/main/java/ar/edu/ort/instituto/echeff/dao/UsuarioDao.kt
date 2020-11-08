package ar.edu.ort.instituto.echeff.dao

import android.util.Log
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Configuracion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface UsuarioDao {

    suspend fun getClienteById(id: String): Cliente {

        var cliente: Cliente = Cliente()

        val questionRef = Firebase.firestore.collection("clientes").document(id)
 //       val query = questionRef.whereEqualTo("id", id)

        try {
            val data = questionRef
                .get()
                .await()
                cliente = data.toObject<Cliente>()!!



        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return cliente
    }

    suspend fun getClienteByUserId(userId: String): Cliente {

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

    suspend fun getChefById(id: String): Chef {

        var chef: Chef = Chef()

        val questionRef = Firebase.firestore.collection("chefs").document(id)
        //val query = questionRef.whereEqualTo("id", id)

        try {
            val data = questionRef
                .get()
                .await()
                chef = data.toObject<Chef>()!!


        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return chef
    }

    suspend fun getChefByUserId(userId: String): Chef {

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
             query
                .add(chef).addOnSuccessListener { result ->
                    val id = result.id
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
            query
                .add(cliente).addOnSuccessListener { result ->
                    val id = result.id
                    cliente.id = id
                    query.document(id).set(cliente)
                }
                .await()
            return cliente
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun createConfiguracion(uid: String): Configuracion {
        var config = Configuracion()
        config.uid = uid
        val questionRef = Firebase.firestore.collection("configuraciones")
        val query = questionRef
        try {
            query
                .document(config.uid)
                .set(config)
                .await()
        } catch (e: Exception) {
            throw e
        }
        return config
    }

    suspend fun getConfiguracionByUID(uid: String): Configuracion {
        var config = Configuracion()
        val questionRef = Firebase.firestore.collection("configuraciones")
        val query = questionRef.whereEqualTo("uid", uid)
        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                config = document.toObject<Configuracion>()
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return config
    }

    suspend fun updateConfiguracion(config: Configuracion): Configuracion {
        val questionRef = Firebase.firestore.collection("configuraciones")
        val query = questionRef
        try {
            query.document(config.uid).set(config)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return config
    }


}