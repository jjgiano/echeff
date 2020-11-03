package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Reserva

import kotlinx.coroutines.launch

class ViewModelDetalleReservaFragment : ViewModel(), UsuarioDao {


    var buscar = MutableLiveData<Boolean>()
    var cliente = MutableLiveData<Cliente>()
    var urlImagen = MutableLiveData<String>()


    fun buscarCliente(id: String): Boolean {
        viewModelScope.launch {
            cliente.value =  getClienteById(id)
        }
        return false
    }

    fun setBuscar(id : String) {
        buscar.value = buscarCliente(id)
    }
}