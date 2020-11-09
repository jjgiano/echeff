package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.launch

class ViewModelReservasModificarFragment : ViewModel(), ReservaDao,PropuestaDao, UsuarioDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(idUsuario: String): MutableList<Reserva>? {

        var lista: MutableList<Reserva>?

        lista = buscarReservasAConfirmar(idUsuario).value

        return lista

    }

    private fun buscarReservasAConfirmar(idUsuario: String): MutableLiveData<MutableList<Reserva>> {
        var listaPropuestas : MutableList<Propuesta>
        val id: String = idUsuario
        var listaReserva : MutableList<Reserva> = ArrayList()
        var cliBuscado = Cliente()

        viewModelScope.launch {
            listaReserva.clear()

            listaPropuestas = getPropuestaByChef(id)
            //Por cada propuesta del Chef que esta MODIFICAR busco la Reserva que le corresponde
            for (item in listaPropuestas) {
                if (item.idEstadoPropuesta == EstadoPropuesta.MODIFICADO.id) {
                    listaReserva.add(getReservaById(item.idReserva))

                }
            }
            //Por cada reserva Busco la Foto del Usuario y la guardo en la Reserva
            for (reserva in listaReserva) {
                cliBuscado = getClienteByUserId(reserva.idUsuario)
                if (cliBuscado.urlFoto.isNotEmpty()) {
                    reserva.urlImg = cliBuscado.urlFoto
                } else {
                    reserva.urlImg = EcheffUtilities.SIN_FOTO.valor
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

