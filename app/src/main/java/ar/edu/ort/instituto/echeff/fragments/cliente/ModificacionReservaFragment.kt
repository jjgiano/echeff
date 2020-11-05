package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ModificacionReservaFragment : Fragment(), PropuestaDao, ReservaDao {
    lateinit var propuesta: Propuesta
    lateinit var reserva: Reserva

    lateinit var v: View
    private lateinit var btnEnviarModificaion: Button
    private lateinit var txtCambiosReserva: EditText
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_modificacion_reserva, container, false)



        btnEnviarModificaion = v.findViewById(R.id.btnEnviarModificaion)
        txtCambiosReserva = v.findViewById(R.id.txtCambiosReserva)

        return v
    }


    override fun onStart() {
        super.onStart()
        this.setSharedPreferences()
        var idPropuesta = this.sharedPreferences.getString("idPropuesta","0")!!

        // setear la modificacion a la propuesta
        // cambiar estado de la propuesta a MODIFICADO
        // cambiar estado de la reserva asociada a MODIFICADO
        // TODO: las demas propuestas de la reserva pasan a PAUSADO
        btnEnviarModificaion.setOnClickListener() {

            val parentJob = Job()
            val scope = CoroutineScope(Dispatchers.Default + parentJob)
            scope.launch {
                propuesta = super.getPropuestaById(idPropuesta)
                propuesta.modificaciones = txtCambiosReserva.text.toString()
                propuesta.idEstadoPropuesta = EstadoPropuesta.MODIFICADO.id
                super<PropuestaDao>.update(propuesta)

                reserva = super<ReservaDao>.getReservaById(propuesta.idReserva)
                reserva.idEstadoReserva = EstadoReserva.MODIFICADA.id
                super<ReservaDao>.update(reserva)

            }


            this.volverVistaHome();
        }
    }


    private fun volverVistaHome() {

        var resultadoMensajeScreen =
            ModificacionReservaFragmentDirections.actionModificacionReservaFragmentToResultadoMensajeFragment(
                TipoResultadoMensaje.MODIFCAR_RESERVA
            )
        v.findNavController().navigate(resultadoMensajeScreen);
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }
}