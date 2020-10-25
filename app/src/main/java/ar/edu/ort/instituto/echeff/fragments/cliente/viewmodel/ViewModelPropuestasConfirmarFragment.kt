package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao

import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.launch

class ViewModelPropuestasConfirmarFragment : ViewModel(), ReservaDao {



 fun pasarAConfirmar(reserva: Reserva) {

        viewModelScope.launch {
            cambiarEstado(reserva, 3)
        }


    }
}