package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch


class ViewModelReservasFinalizadasFragment : ViewModel(), ReservaDao {
    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun setCargar(idUsuario: String) {
        cargar.value = true
        viewModelScope.launch {
            liveDataList.postValue(super.getReservasFinalizadas(idUsuario))
        }
    }
}