package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelReservasDisponiblesFragment : ViewModel(), ReservaDao {

    var liveDataList = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()




    private fun buscarReservasDisponibles(): MutableLiveData<MutableList<Reserva>> {

        viewModelScope.launch {
            liveDataList.postValue(getReservasDisponibles())
        }
        return liveDataList
    }


    fun setcargar() {
        cargar.value
         buscarReservasDisponibles().value
    }

}