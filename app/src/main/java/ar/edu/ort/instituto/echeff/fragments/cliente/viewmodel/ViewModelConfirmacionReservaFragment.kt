package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Propuesta
import kotlinx.coroutines.launch

class ViewModelConfirmacionReservaFragment : ViewModel(), PropuestaDao, UsuarioDao {
    var liveDataPropuesta = MutableLiveData<Propuesta>()
    var liveDataChef = MutableLiveData<Chef>()

    fun loadPropuesta(idPropuesta: String) {
        viewModelScope.launch {
            var propuesta = super.getPropuestaById(idPropuesta)
            liveDataPropuesta.postValue(propuesta)
        }
    }

    fun loadChef(idChef: String) {
        viewModelScope.launch {
            var chef = super<UsuarioDao>.getChefById(idChef)
            liveDataChef.postValue(chef)
        }
    }
}