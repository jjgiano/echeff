package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Configuracion
import ar.edu.ort.instituto.echeff.entities.Usuario
import kotlinx.coroutines.launch

class ViewModelRegistroUsuarioFragment : ViewModel(), UsuarioDao {

    var isChef = MutableLiveData<Boolean>()
    var chefa = MutableLiveData<Chef>()
    var cliente = MutableLiveData<Cliente>()

    fun getUsuarioLogueado(userId: String) {
        viewModelScope.launch {
            var chef = super.getChefByUserId(userId)
            isChef.value = chef.id.isNotEmpty()
        }
    }

    fun addChefLogueado(chef: Chef){
        viewModelScope.launch{
            chefa.postValue(super.addChef(chef))
            isChef.value = true
        }
    }

    fun addClienteLogueado(cli: Cliente){
        viewModelScope.launch{
            cliente.postValue(super.addCliente(cli))
            isChef.value=false
        }
    }
}
