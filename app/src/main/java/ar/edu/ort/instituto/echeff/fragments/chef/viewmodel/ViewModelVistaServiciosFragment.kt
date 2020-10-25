package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.*

class ViewModelVistaServiciosFragment : ViewModel(), ServicioDao, ReservaDao, PropuestasDao {

    var listaLiveData = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()

    fun getdatos(idChef : String): MutableList<Reserva>? {

        var listaMutableList : MutableList<Reserva>?

        listaMutableList = buscarDatos(idChef).value

        return listaMutableList

    }

    private fun buscarDatos(idChef : String): MutableLiveData<MutableList<Reserva>> {

        viewModelScope.launch {
            listaLiveData.postValue(getAllReservas())
        }

        return listaLiveData

    }

    fun setcargar(idChef: String) {
        cargar.value
        getdatos(idChef)
    }
}