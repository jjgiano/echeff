package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import kotlinx.coroutines.launch

class ViewModelDetallePropuestaFragment : ViewModel(), PropuestasDao {

    var ListDataPropuesta = MutableLiveData<MutableList<Propuesta>>()
    var buscar = MutableLiveData<Boolean>()

    fun getPropuesta(idChef: String): MutableList<Propuesta>? {

        var propuesta: MutableList<Propuesta>?

        propuesta = buscarPropuesta(idChef).value

        return propuesta

    }

    private fun buscarPropuesta(idChef: String): MutableLiveData<MutableList<Propuesta>> {


        viewModelScope.launch {
            ListDataPropuesta.postValue(getPropuestaByChef(idChef))
        }

        return ListDataPropuesta

    }

    fun setBuscar(idChef : String) {
        buscar.value
        getPropuesta(idChef)
    }
}