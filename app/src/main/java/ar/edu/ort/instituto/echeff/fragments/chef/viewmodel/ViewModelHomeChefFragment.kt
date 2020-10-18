package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.*


class ViewModelHomeChefFragment : ViewModel(), ReservaDao {

    var listaLiveData = MutableLiveData<MutableList<Reserva>>()
    lateinit var cargar : MutableLiveData<Boolean>

    fun getLista() :MutableList<Reserva>? {

        var listaMutableList : MutableList<Reserva>?
        listaMutableList = buscarListaReserva().value
        return listaMutableList

    }

    fun buscarListaReserva() : MutableLiveData<MutableList<Reserva>> {
        val parentJob = Job()
        val handler = CoroutineExceptionHandler { _, throwable ->
            Log.d("demo", "handler: $throwable") // Prints "handler: java.io.IOException"
        }
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            listaLiveData.postValue(getAllReservas())
        }

        return listaLiveData

    }

    fun setCargar() : Boolean {
        cargar.setValue(true)
        return true
    }

}
