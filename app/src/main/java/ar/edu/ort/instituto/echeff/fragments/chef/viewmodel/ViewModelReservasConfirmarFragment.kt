package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.*
import kotlinx.coroutines.launch

class ViewModelReservasConfirmarFragment : ViewModel(), ReservaDao, PropuestaDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(): MutableList<Reserva>? {

        var lista: MutableList<Reserva>?

        lista = buscarReservasAConfirmar().value

        return lista

    }

    private fun buscarReservasAConfirmar(): MutableLiveData<MutableList<Reserva>> {
        var listaPropuestas : MutableList<Propuesta> = ArrayList<Propuesta>()
        val id = "1" //TODO CAMBIAR EL 1 POR EL VALOR EN SHAREDPREFERENCE
        var listaReserva : MutableList<Reserva> = ArrayList<Reserva>()

        viewModelScope.launch {
            listaPropuestas = getPropuestaByChef(id)

            for (item in listaPropuestas) {
                if (item.idEstadoPropuesta == EstadoPropuesta.ACONFIRMAR.id) {
                    listaReserva.add(getReservaById(item.idReserva))

                }
            }
            liveDataList.postValue(listaReserva)
        }

        return liveDataList

    }


    fun setcargar() {
        cargar.value
        getLista()
    }

    fun pasarAConfirmar(reserva: Reserva) {
        viewModelScope.launch {
            cambiarEstado(reserva, EstadoReserva.ACONFIRMAR.id)
        }
    }

}

