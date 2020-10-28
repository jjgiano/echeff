package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.EstadoServicio
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import kotlinx.coroutines.launch

class ViewModelDetalleServicioFragment : ViewModel(), ReservaDao, PropuestaDao, ServicioDao {

    var propuesta = MutableLiveData<Propuesta>()
    var reserva = MutableLiveData<Reserva>()

    var buscar = MutableLiveData<Boolean>()

    private fun buscarDatos(servicio: Servicio) : Boolean {

        viewModelScope.launch {
            propuesta.value = getPropuestaById(servicio.idPropuesta)

            reserva.value = getReservaById(servicio.idReserva)

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

}