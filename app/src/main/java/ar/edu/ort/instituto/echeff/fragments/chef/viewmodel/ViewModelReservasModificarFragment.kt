package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelReservasModificarFragment : ViewModel(), ReservaDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getLista(): MutableList<Reserva>? {

        var lista: MutableList<Reserva>?

        lista = buscarReservasAConfirmar().value

        return lista

    }

    private fun buscarReservasAConfirmar(): MutableLiveData<MutableList<Reserva>> {

        viewModelScope.launch {
            liveDataList.postValue(getReservasAModificar())
        }

        return liveDataList

    }


    fun setcargar() {
        cargar.value
        getLista()
    }

}

