package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.EstadoPropuesta
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelReservasModificarFragment : ViewModel(), ReservaDao,PropuestaDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(): MutableList<Reserva>? {

        var lista: MutableList<Reserva>?

        lista = buscarReservasAConfirmar().value

        return lista

    }

    private fun buscarReservasAConfirmar(): MutableLiveData<MutableList<Reserva>> {
        var listaPropuestas : MutableList<Propuesta> = ArrayList<Propuesta>()
        val id = "1"  //TODO: remplazar el 1 por el dato en SharedPreference
        var listaReserva : MutableList<Reserva> = ArrayList<Reserva>()

        viewModelScope.launch {
            listaPropuestas = getPropuestaByChef(id)

            for (item in listaPropuestas) {
                if (item.idEstadoPropuesta == EstadoPropuesta.MODIFICADO.id) {
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

}

