package ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PerfilChefDao
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import kotlinx.coroutines.launch

class ViewModelCalificarServicioFragment : ViewModel(), PerfilChefDao {


    fun cargarNuevaPuntuacion(puntuacion: Puntuacion) {
        viewModelScope.launch {
            addPuntuacion(puntuacion)
        }
    }
}