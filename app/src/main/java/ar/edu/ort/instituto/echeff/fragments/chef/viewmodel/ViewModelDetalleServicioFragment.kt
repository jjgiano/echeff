package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.*
import kotlinx.coroutines.launch

class ViewModelDetalleServicioFragment : ViewModel(), ReservaDao, PropuestaDao, ServicioDao, UsuarioDao {

    var propuesta = MutableLiveData<Propuesta>()
    var reserva = MutableLiveData<Reserva>()
    var cliente = MutableLiveData<Cliente>()
    var buscar = MutableLiveData<Boolean>()

    private fun buscarDatos(servicio: Servicio) : Boolean {

        viewModelScope.launch {
            propuesta.value = getPropuestaById(servicio.idPropuesta)

            reserva.value = getReservaById(servicio.idReserva)
            cliente.value =  getClienteByUserId(reserva.value!!.idUsuario)

        }
        return true
    }

    fun setBuscar(servicio: Servicio) {
        buscar.value = buscarDatos(servicio)
    }


    fun pasarAFinalizado (servicio: Servicio) {
        viewModelScope.launch {
            cambiarEstado(servicio, EstadoServicio.FINALIZADO.id)
        }
    }
    fun pasarAFinalizadoReserva (reserva: Reserva) {
        viewModelScope.launch {
            cambiarEstado(reserva, EstadoReserva.FINALIZADA.id)
        }
    }
    fun pasarAFinalizadoPropuesta (propuesta: Propuesta) {
        viewModelScope.launch {
            cambiarEstado(propuesta, EstadoPropuesta.FINALIZADO.id)
        }
    }

}