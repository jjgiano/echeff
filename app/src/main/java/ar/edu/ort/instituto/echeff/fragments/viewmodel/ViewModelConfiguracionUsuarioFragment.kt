package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Configuracion
import ar.edu.ort.instituto.echeff.entities.Problema
import kotlinx.coroutines.launch

class ViewModelConfiguracionUsuarioFragment : ViewModel(), UsuarioDao {

    fun crearConfiguracion(configuracion: Configuracion) {
        viewModelScope.launch {
            super.addConfiguracion(configuracion)
        }
    }
}
