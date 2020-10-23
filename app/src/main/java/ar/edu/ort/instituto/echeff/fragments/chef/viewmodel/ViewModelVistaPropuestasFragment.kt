package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import kotlinx.coroutines.*

class ViewModelVistaPropuestasFragment : ViewModel(), PropuestasDao {

    var listaLiveData = MutableLiveData<MutableList<Propuesta>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(idChef : String): MutableList<Propuesta>? {

        var listaMutableList : MutableList<Propuesta>?

        listaMutableList = buscarListaPropuesta(idChef).value

        return listaMutableList

    }

    private fun buscarListaPropuesta(idChef : String): MutableLiveData<MutableList<Propuesta>> {

        viewModelScope.launch {
            listaLiveData.postValue(getPropuestaByChef(idChef))
        }

        return listaLiveData

    }

    fun setcargar(idChef: String) {
        cargar.value
        getLista(idChef)
    }
}