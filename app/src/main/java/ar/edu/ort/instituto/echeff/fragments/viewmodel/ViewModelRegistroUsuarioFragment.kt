package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import kotlinx.coroutines.launch

class ViewModelRegistroUsuarioFragment : ViewModel(), UsuarioDao {

    var isChef = MutableLiveData<Boolean>()
    var newChef = MutableLiveData<Chef>()
    var newCliente = MutableLiveData<Cliente>()

    fun getUsuarioLogueado(userId: String) {
        viewModelScope.launch {
            var chef = super.getChefByUserId(userId)
            isChef.value = chef.id.isNotEmpty()
        }
    }


    fun addChefLogueado(chef: Chef){
        viewModelScope.launch{
            newChef.postValue(super.addChef(chef))
        }
    }


    fun addClienteLogueado(cli: Cliente){
        viewModelScope.launch{
            newCliente.postValue(super.addCliente(cli))
        }
    }


}
