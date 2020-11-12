package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.HistoriaDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelDetalleHistoriaFragmetn :  ViewModel(),  HistoriaDao{

    var buscar = MutableLiveData<Boolean>()
    var listDataHistoria = MutableLiveData<MutableList<Historia>>()
    var chef  = MutableLiveData<Historia>()

    private fun buscarHistoria(idChef: String): Boolean {
        viewModelScope.launch {

            listDataHistoria.postValue(getHistorasByChef(idChef))
        }
        return true

    }

    fun setBuscar(idChef : String) {
        buscar.value = buscarHistoria(idChef)

    }

    fun actualizarHistoria(historiaChef: Historia) {
        viewModelScope.launch {
            update(historiaChef)
        }
    }
}