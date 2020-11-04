package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.HistoriaDao
import ar.edu.ort.instituto.echeff.dao.PerfilChefDao
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import kotlinx.coroutines.launch

class ViewModelPerfilChefFragment: ViewModel(), PerfilChefDao, HistoriaDao{

    var dataPerfil = MutableLiveData<PerfilChef>()
    var buscar = MutableLiveData<Boolean>()
    var listDataHistoria = MutableLiveData<MutableList<Historia>>()

    private fun buscarPerfil(idChef: String): Boolean {
        viewModelScope.launch {
            dataPerfil.value = getPerfilByIdChef(idChef)

            listDataHistoria.postValue(getHistorasByChef(idChef))
        }
        return true

    }

    fun setBuscar(idChef : String) {
        buscar.value = buscarPerfil(idChef)
    }

    fun actualizarPerfil(perfilChef: PerfilChef) {
        viewModelScope.launch {
            update(perfilChef)
        }
    }
}