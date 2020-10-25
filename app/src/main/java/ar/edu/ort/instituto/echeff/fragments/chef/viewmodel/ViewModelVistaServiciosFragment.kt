package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import kotlinx.coroutines.*

class ViewModelVistaServiciosFragment : ViewModel(), ServicioDao, ReservaDao, PropuestaDao {

    var listaLiveData = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()


    fun getdatos(): MutableList<Reserva>? {

        var listaMutableList : MutableList<Reserva>?

        listaMutableList = buscarDatos().value

        return listaMutableList

    }

    private fun buscarDatos(): MutableLiveData<MutableList<Reserva>> {

        val id = "1" //TODO CAMBIAR EL 1 POR EL VALOR EN SHAREDPREFERENCE
        var listaReserva : MutableList<Reserva> = ArrayList<Reserva>()
        var listaServicios : MutableList<Servicio> = ArrayList<Servicio>()
        viewModelScope.launch {
            listaServicios = getServiciosPendientesByChef(id)

            for(item in listaServicios) {
                listaReserva.add(getReservaById(item.idReserva))
            }
            listaLiveData.postValue(listaReserva)

        }

        return listaLiveData

    }

    fun setcargar() {
        cargar.value
        getdatos()
    }



}