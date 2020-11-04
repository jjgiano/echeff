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
            if (chef.id.isNotEmpty()) {
                isChef.postValue(true)
            } else {
                var cliente = super.getClienteByUserId(userId)
                if (cliente.id.isNotEmpty()) {
                    isChef.postValue(false)
                }
            }
        }
    }

    fun addChefLogueado(chef: Chef){
        viewModelScope.launch{
            chefa.postValue(super.addChef(chef))
        }
    }

    fun addClienteLogueado(cli: Cliente){
        viewModelScope.launch{
            cliente.postValue(super.addCliente(cli))
        }
    }
}
