package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.entities.Problema
import kotlinx.coroutines.launch

class ViewModelMesaAyudaFragment : ViewModel(), ServicioDao {

    fun crearProblema(problema: Problema) {
        viewModelScope.launch {
            super.addProblema(problema)
        }
    }
}