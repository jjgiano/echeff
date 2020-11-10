package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PerfilChefDao
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import kotlinx.coroutines.launch

class ViewModelCalificarServicioFragment : ViewModel(), PerfilChefDao, PropuestaDao {

    var liveDataPropuesta = MutableLiveData<Propuesta>()

    fun cargarNuevaPuntuacion(puntuacion: Puntuacion) {
        viewModelScope.launch {
            addPuntuacion(puntuacion)
        }
    }

    fun getPropuestaByReserva(idReserva: String){
        viewModelScope.launch {
            var propuesta = super.getPropuestaByReservaFinalizada(idReserva)
            liveDataPropuesta.postValue(propuesta)
        }
    }
}