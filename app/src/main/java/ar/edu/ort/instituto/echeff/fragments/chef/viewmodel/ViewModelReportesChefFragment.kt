package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.PuntuacionDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.*
import kotlinx.coroutines.launch

class ViewModelReportesChefFragment : ViewModel(), PropuestaDao, UsuarioDao, PuntuacionDao {

    var liveDataList = MutableLiveData<MutableList<Propuesta>>()
    var cargar = MutableLiveData<Boolean>()
    var chef = MutableLiveData<Chef>()
    var listaPuntajes = MutableLiveData<MutableList<Puntuacion>>()



    fun getLista(idUsuario: String): MutableList<Propuesta>? {

        var lista: MutableList<Propuesta>?

        lista = buscarPropuestas(idUsuario).value

        return lista

    }

    private fun buscarPropuestas(idUsuario:String): MutableLiveData<MutableList<Propuesta>> {
        var listaPropuestas : MutableList<Propuesta>

        viewModelScope.launch {
            listaPropuestas = getPropuestaByChef(idUsuario)

            chef.value = getChefByUserId(idUsuario)

            listaPuntajes.value = getPuntuacionByChef(idUsuario)

            liveDataList.postValue(listaPropuestas)
        }

        return liveDataList

    }

    fun setcargar(idUsuario:String) {
        cargar.value = false
        getLista(idUsuario)
    }

}