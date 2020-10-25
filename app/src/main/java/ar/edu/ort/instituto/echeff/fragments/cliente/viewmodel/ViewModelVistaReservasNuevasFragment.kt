package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Usuario
import kotlinx.coroutines.launch

    class ViewModelVistaReservasNuevasFragment : ViewModel(), ReservaDao {

        var liveDataList = MutableLiveData<MutableList<Reserva>>()
        var cargar = MutableLiveData<Boolean>()

        fun getLista(idUsuario: String): MutableList<Reserva>? {

            var lista: MutableList<Reserva>?

            lista = buscarReservasNuevas(idUsuario).value

            return lista

        }

        private fun buscarReservasNuevas(idUsuario: String): MutableLiveData<MutableList<Reserva>> {

            viewModelScope.launch {
                liveDataList.postValue(getReservasNuevas(idUsuario))
            }

            return liveDataList

        }


        fun setCargar(idUsuario: String) {
            cargar.value
            getLista(idUsuario)
        }

    }