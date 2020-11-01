package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Tarjeta
import kotlinx.coroutines.launch


class ViewModelPagoReservaFragment : ViewModel(), ReservaDao {
    var tarjetaUsuario = MutableLiveData<Tarjeta>()


    fun setTarjeta(tarjeta: Tarjeta) {
        viewModelScope.launch {
            setTarjetaDeCredito(tarjeta)
        }
    }

    fun getTarjeta(idUsuario: String) {
        viewModelScope.launch {
            tarjetaUsuario.value = getTarjetaUsuario(idUsuario)
        }
    }
}