package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import kotlinx.coroutines.launch

class ViewModelHomeClienteFragment : ViewModel(), ReservaDao, PropuestaDao, ServicioDao {
    var liveDataReservasProximasList = MutableLiveData<MutableList<Reserva>>()
    var liveDataPropuestasAConfirmarList = MutableLiveData<MutableList<Propuesta>>()
    var liveDataReservasPendientesList = MutableLiveData<MutableList<Reserva>>()
    var liveDataPropuestasDestacadasList = MutableLiveData<MutableList<Propuesta>>()
    var serviciosPendientes = MutableLiveData<MutableList<Servicio>>()
    var liveDataBooleanCargar = MutableLiveData<Boolean>()

    fun setCargar(idUsuario: String) {
        liveDataBooleanCargar.value = true
        viewModelScope.launch {
            liveDataReservasProximasList.postValue(super.getReservasPagadas(idUsuario))
        }

        viewModelScope.launch {
            liveDataPropuestasAConfirmarList.postValue(super.getPropuestasAConfirmar(idUsuario))
        }

        viewModelScope.launch {
            liveDataReservasPendientesList.postValue(super.getReservasPendientes(idUsuario))
        }

        viewModelScope.launch {
            liveDataPropuestasDestacadasList.postValue(super.getPropuestasDestacadas())
        }

        viewModelScope.launch {
            serviciosPendientes.postValue(getServiciosPendientesByCliente(idUsuario))
        }

    }
}