package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import kotlinx.coroutines.*

class ViewModelVistaServiciosFragment : ViewModel(), ServicioDao, ReservaDao {

    var listaLiveData = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()
    var serviciosPendientes = MutableLiveData<MutableList<Servicio>>()
    var serviciosRealizados = MutableLiveData<MutableList<Servicio>>()
    var listaLiveDataFinalizados = MutableLiveData<MutableList<Reserva>>()


    private fun buscarDatos(idUsuario: String): Boolean {

        val id: String = idUsuario
        var listaReserva: MutableList<Reserva> = ArrayList()
        var listaReservaRealizados: MutableList<Reserva> = ArrayList()
        var listaServiciosPendientes: MutableList<Servicio>
        var listaServiciosFinalizados: MutableList<Servicio>

        viewModelScope.launch {

            listaServiciosPendientes = getServiciosPendientesByChef(id)
            serviciosPendientes.value = listaServiciosPendientes

            for (item in listaServiciosPendientes) {
                listaReserva.add(getReservaById(item.idReserva))
            }
            listaLiveData.postValue(listaReserva)

            listaServiciosFinalizados = getServiciosFinalizadosByChef(id)
            serviciosRealizados.value = listaServiciosFinalizados

            for (item in listaServiciosFinalizados) {
                listaReservaRealizados.add(getReservaById(item.idReserva))
            }
            listaLiveDataFinalizados.postValue(listaReservaRealizados)

        }

        return true

    }

    fun setcargar(idUsuario: String) {
        cargar.value = buscarDatos(idUsuario)
    }


}