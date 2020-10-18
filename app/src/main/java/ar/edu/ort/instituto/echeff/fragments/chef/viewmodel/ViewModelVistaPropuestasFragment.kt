package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import kotlinx.coroutines.*

class ViewModelVistaPropuestasFragment : ViewModel(), PropuestasDao {

    var listaLiveData = MutableLiveData<MutableList<Propuesta>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(): MutableList<Propuesta>? {

        var listaMutableList: MutableList<Propuesta>?

        listaMutableList = buscarListaPropuesta().value

        return listaMutableList

    }

    private fun buscarListaPropuesta(): MutableLiveData<MutableList<Propuesta>> {
        val parentJob = Job()

        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            listaLiveData.postValue(getAll())
        }

        return listaLiveData

    }

    fun setcargar() {
        cargar.value
        getLista()
    }
}