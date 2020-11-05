package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Configuracion
import kotlinx.coroutines.launch

class ViewModelConfiguracionUsuarioFragment : ViewModel(), UsuarioDao {

    var liveDataConfig = MutableLiveData<Configuracion>()

    fun getConfiguracion(uid: String) {
        viewModelScope.launch {
            var config = super.getConfiguracionByUID(uid)
            if (config.uid.isEmpty()){
               config = super.createConfiguracion(uid)
            }
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
