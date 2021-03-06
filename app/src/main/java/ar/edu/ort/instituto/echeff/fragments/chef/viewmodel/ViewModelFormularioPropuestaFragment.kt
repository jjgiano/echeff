package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao

import ar.edu.ort.instituto.echeff.entities.Propuesta
import kotlinx.coroutines.launch

class ViewModelFormularioPropuestaFragment : ViewModel(), PropuestaDao {


    fun guardarPropuesta(propuesta: Propuesta) {
        viewModelScope.launch {
            add(propuesta)
        }
    }

    fun modificarPropuesta(propuestaNueva: Propuesta) {
        viewModelScope.launch {
            update(propuestaNueva)
        }
    }


}