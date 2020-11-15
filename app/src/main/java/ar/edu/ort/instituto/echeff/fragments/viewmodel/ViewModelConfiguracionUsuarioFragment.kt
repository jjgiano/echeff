package ar.edu.ort.instituto.echeff.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Configuracion
import kotlinx.coroutines.launch

class ViewModelConfiguracionUsuarioFragment : ViewModel(), UsuarioDao {

    var liveDataConfig = MutableLiveData<Configuracion>()
    var chef = MutableLiveData<Chef>()
    var cliente = MutableLiveData<Cliente>()

    fun getConfiguracion(uid: String) {
        viewModelScope.launch {
            var config = super.getConfiguracionByUID(uid)
            if (config.uid.isEmpty()) {
                config = super.createConfiguracion(uid)
            }
            cliente.value = getClienteByUserId(uid)
            chef.value = getChefByUserId(uid)

            liveDataConfig.postValue(config)
        }
    }

    fun changeConfiguracion(config: Configuracion) {
        viewModelScope.launch {
            super.updateConfiguracion(config)
            liveDataConfig.postValue(config)
        }
    }



}
