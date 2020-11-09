package ar.edu.ort.instituto.echeff.fragments.chef.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.dao.ServicioDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.*

class ViewModelVistaServiciosFragment : ViewModel(), ServicioDao, ReservaDao, UsuarioDao {

    var listaLiveData = MutableLiveData<MutableList<Reserva>>()
    var cargar = MutableLiveData<Boolean>()
    var serviciosPendientes = MutableLiveData<MutableList<Servicio>>()
    var serviciosRealizados = MutableLiveData<MutableList<Servicio>>()
    var listaLiveDataFinalizados = MutableLiveData<MutableList<Reserva>>()


    private fun buscarDatos(idUsuario: String): Boolean {

        val id: String = idUsuario
        var listaReserva: MutableList<Reserva> = ArrayList()
        var listaReservaRealizados: MutableList<Reserva> = ArrayList()
        var listaServiciosPendientes: MutableList<Servicio>
        var listaServiciosFinalizados: MutableList<Servicio>
        var cliBuscado = Cliente()

        viewModelScope.launch {

            listaServiciosPendientes = getServiciosPendientesByChef(id)
            serviciosPendientes.value = listaServiciosPendientes

            for (item in listaServiciosPendientes) {
                listaReserva.add(getReservaById(item.idReserva))
            }

            //Por cada reserva Busco la Foto del Usuario y la guardo en la Reserva
            for (reserva in listaReserva) {
                cliBuscado = getClienteByUserId(reserva.idUsuario)
                if (cliBuscado.urlFoto.isNotEmpty()) {
                    reserva.urlImg = cliBuscado.urlFoto
                }else {
                    reserva.urlImg = EcheffUtilities.SIN_FOTO.valor
                }
            }


            listaLiveData.postValue(listaReserva)

            listaServiciosFinalizados = getServiciosFinalizadosByChef(id)
            serviciosRealizados.value = listaServiciosFinalizados
            cliBuscado = Cliente()
            for (item in listaServiciosFinalizados) {
                listaReservaRealizados.add(getReservaById(item.idReserva))
            }
            //Por cada reserva Busco la Foto del Usuario y la guardo en la Reserva
            for (reserva in listaReservaRealizados) {
                cliBuscado = getClienteByUserId(reserva.idUsuario)
                if (cliBuscado.urlFoto.isNotEmpty()) {
                    reserva.urlImg = cliBuscado.urlFoto
                }else {
                    reserva.urlImg = EcheffUtilities.SIN_FOTO.valor
                }
            }


            listaLiveDataFinalizados.postValue(listaReservaRealizados)

        }

        return true

    }

    fun setcargar(idUsuario: String) {
        cargar.value = buscarDatos(idUsuario)
    }


}