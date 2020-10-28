package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelHomeClienteFragment : ViewModel(), ReservaDao, PropuestaDao {
        var liveDataReservasProximasList = MutableLiveData<MutableList<Reserva>>()
        var liveDataReservasAConfirmarList = MutableLiveData<MutableList<Reserva>>()
        var liveDataReservasPendientesList = MutableLiveData<MutableList<Reserva>>()
        var liveDataPropuestasDestacadasList = MutableLiveData<MutableList<Propuesta>>()
        var liveDataBooleanCargar = MutableLiveData<Boolean>()

        fun setCargar(idUsuario: Number) {
            liveDataBooleanCargar.value = true
            viewModelScope.launch {
                liveDataReservasProximasList.postValue(super.getReservasPagadas(idUsuario))
            }
            viewModelScope.launch {
                liveDataReservasAConfirmarList.postValue(super.getReservasAConfirmar(idUsuario))
            }
            viewModelScope.launch {
                liveDataReservasPendientesList.postValue(super.getReservasPendientes(idUsuario))
            }
            viewModelScope.launch {
                liveDataPropuestasDestacadasList.postValue(super.getPropuestasDestacadas())
            }
        }
    }