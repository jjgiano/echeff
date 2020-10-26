package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.TipoServicio
import kotlinx.coroutines.launch

class ViewModelReservaDosFragment : ViewModel(), ServicioDao {
    var tipoServicioLiveData = MutableLiveData<MutableList<TipoServicio>>()

    fun obtenerTipoServicios(): MutableLiveData<MutableList<TipoServicio>> {
        viewModelScope.launch {
            tipoServicioLiveData.postValue(getTipoServicios())
        }
        return tipoServicioLiveData
    }
}