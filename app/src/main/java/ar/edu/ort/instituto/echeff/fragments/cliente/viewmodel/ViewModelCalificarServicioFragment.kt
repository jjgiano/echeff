package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PerfilChefDao
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.PuntuacionDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import kotlinx.coroutines.launch

class ViewModelCalificarServicioFragment : ViewModel(), PuntuacionDao, PropuestaDao {

    var propuesta = MutableLiveData<Propuesta>()
    var puntuacion = MutableLiveData<Puntuacion>()

    fun cargarNuevaPuntuacion(puntuacion: Puntuacion) {
        viewModelScope.launch {
            addPuntuacion(puntuacion)
        }
    }

    fun getPropuestaByReserva(idReserva: String){
        viewModelScope.launch {
            val result = super.getPropuestaByReservaFinalizada(idReserva)
            propuesta.postValue(result)
        }
    }

    fun getPuntuacionActual(idReserva: String){
        viewModelScope.launch {
            val result = getPuntuacionByReserva(idReserva);
            puntuacion.postValue(result)
        }
    }
}