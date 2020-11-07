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
import ar.edu.ort.instituto.echeff.entities.Tarjeta
import kotlinx.coroutines.launch


class ViewModelPagoReservaFragment : ViewModel(), ReservaDao, PropuestaDao, ServicioDao {
    var tarjetaUsuario = MutableLiveData<Tarjeta>()
    var propuesta = MutableLiveData<Propuesta>()
    var reserva = MutableLiveData<Reserva>()


    fun setTarjeta(tarjeta: Tarjeta) {
        viewModelScope.launch {
            setTarjetaDeCredito(tarjeta)
        }
    }

    fun getTarjeta(idUsuario: String) {
        viewModelScope.launch {
            tarjetaUsuario.value = getTarjetaUsuario(idUsuario)
        }
    }

    fun getPropuesta(idPropuesta: String) {
        viewModelScope.launch {
            propuesta.value = getPropuestaById(idPropuesta)
        }
    }

    fun getReserva(idReserva: String) {
        viewModelScope.launch {
            reserva.value = getReservaById(idReserva)
        }
    }

    fun actualizarPropuesta(newPropuesta: Propuesta) {
        viewModelScope.launch {
            update(newPropuesta)
        }
    }

    fun actualizarReserva(newReserva: Reserva) {
        viewModelScope.launch {
            update(newReserva)
        }
    }

    fun generarServicio(newServicio: Servicio) {
        viewModelScope.launch {
            add(newServicio)
        }
    }

}