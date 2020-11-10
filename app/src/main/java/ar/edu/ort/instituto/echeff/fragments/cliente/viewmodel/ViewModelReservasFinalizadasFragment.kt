package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelReservasFinalizadasFragment : ViewModel(), ReservaDao {
    var liveDataReservaList = MutableLiveData<MutableList<Reserva>>()

    fun setCargarReservasFinalizadas(idUsuario: String) {
        viewModelScope.launch {
            liveDataReservaList.postValue(super.getReservasFinalizadas(idUsuario))
        }
    }

}