package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.launch

class ViewModelReservasDisponiblesFragment : ViewModel(), ReservaDao, UsuarioDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    private fun buscarReservasDisponibles(): MutableLiveData<MutableList<Reserva>> {

        viewModelScope.launch {
            var listaReservas:  MutableList<Reserva> = ArrayList<Reserva>()
            var cliBuscado = Cliente()
            listaReservas = getReservasDisponibles()

            //Por cada reserva Busco la Foto del Usuario y la guardo en la Reserva
            for (reserva in listaReservas) {
                cliBuscado = getClienteByUserId(reserva.idUsuario)
                if (cliBuscado.urlFoto.isNotEmpty()) {
                    reserva.urlImg = cliBuscado.urlFoto
                }else {
                    reserva.urlImg = EcheffUtilities.SIN_FOTO.valor
                }
            }

            liveDataList.value = listaReservas

        }
        return liveDataList
    }

    private fun buscarCliente(id: String): Cliente {
        var cliente = Cliente()

        viewModelScope.launch {
            cliente = getClienteByUserId(id)
        }
        return cliente
    }


    fun setcargar() {
        cargar.value
        buscarReservasDisponibles().value

    }

}