package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

    class ViewModelVistaReservasNuevasFragment : ViewModel(), ReservaDao {

        var liveDataList = MutableLiveData<MutableList<Reserva>>()
        var cargar = MutableLiveData<Boolean>()

        fun getLista(): MutableList<Reserva>? {

            var lista: MutableList<Reserva>?

            lista = buscarReservasNuevas().value

            return lista

        }

        private fun buscarReservasNuevas(): MutableLiveData<MutableList<Reserva>> {

            viewModelScope.launch {
                liveDataList.postValue(getReservasNuevas())
            }

            return liveDataList

        }


        fun setcargar() {
            cargar.value
            getLista()
        }

    }