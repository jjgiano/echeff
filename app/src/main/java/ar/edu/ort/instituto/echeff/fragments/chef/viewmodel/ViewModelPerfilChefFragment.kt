package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.*
import ar.edu.ort.instituto.echeff.entities.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelPerfilChefFragment: ViewModel(), PerfilChefDao, HistoriaDao, UsuarioDao, PuntuacionDao, ReservaDao{

    var dataPerfil = MutableLiveData<PerfilChef>()
    var buscar = MutableLiveData<Boolean>()
    var listDataHistoria = MutableLiveData<MutableList<Historia>>()
    var chef  = MutableLiveData<Chef>()
    var listDataPuntuacion = MutableLiveData<MutableList<Puntuacion>>()
    private var listaPuntuaciones : MutableList<Puntuacion> = ArrayList<Puntuacion>()
    var reserva = Reserva()


    private fun buscarPerfil(idChef: String): Boolean {
        viewModelScope.launch {
            delay(1000);
            dataPerfil.value = getPerfilByIdChef(idChef)
            chef.value = getChefByUserId(idChef)
            listDataHistoria.postValue(getHistorasByChef(idChef))
            listaPuntuaciones = (getPuntuacionByChef(idChef))

            for (puntuacion in listaPuntuaciones) {
                reserva = getReservaById(puntuacion.idReserva)
                puntuacion.urlImg = reserva.urlImg
            }

            listDataPuntuacion.postValue(listaPuntuaciones)
        }

        return true

    }

    fun setBuscar(idChef : String) {
        buscar.value = buscarPerfil(idChef)

    }

    fun actualizarPerfil(perfilChef: PerfilChef) {
        viewModelScope.launch {
            update(perfilChef)
        }
    }
}