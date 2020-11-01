package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.*
import kotlinx.coroutines.launch

class ViewModelReservasModificarFragment : ViewModel(), ReservaDao,PropuestaDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(idUsuario: String): MutableList<Reserva>? {

        var lista: MutableList<Reserva>?

        lista = buscarReservasAConfirmar(idUsuario).value

        return lista

    }

    private fun buscarReservasAConfirmar(idUsuario: String): MutableLiveData<MutableList<Reserva>> {
        var listaPropuestas : MutableList<Propuesta> = ArrayList<Propuesta>()
        val id: String = idUsuario
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


    fun setcargar(idUsuario: String) {
        cargar.value
        getLista(idUsuario)
    }


}

