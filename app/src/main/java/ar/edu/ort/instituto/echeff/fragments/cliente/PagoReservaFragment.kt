package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.EstadoPropuesta
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.TipoResultadoMensaje
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagoReservaFragment : Fragment(), PropuestaDao, ReservaDao {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var propuesta: Propuesta
    lateinit var reserva: Reserva

    lateinit var v: View
    lateinit var textViewImporteTotal: TextView
    lateinit var txtNombreTarjeta: EditText
    lateinit var txtNumeroTarjeta: EditText
    lateinit var txtVencimientoTarjeta: EditText
    lateinit var txtCsvTarjeta: EditText
    lateinit var crdMercadoLibre: CardView
    lateinit var buttonConfirmarPago: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pago_reserva, container, false)
        textViewImporteTotal = v.findViewById(R.id.textViewImporteTotal)
        txtNombreTarjeta = v.findViewById(R.id.txtNombreTarjeta)
        txtNumeroTarjeta = v.findViewById(R.id.txtNumeroTarjeta)
        txtVencimientoTarjeta = v.findViewById(R.id.txtVencimientoTarjeta)
        txtCsvTarjeta = v.findViewById(R.id.txtCsvTarjeta)
        crdMercadoLibre = v.findViewById(R.id.crdMercadoLibre)
        buttonConfirmarPago = v.findViewById(R.id.buttonConfirmarPago)

        return v;
    }

    override fun onStart() {
        super.onStart()
        this.setSharedPreferences()

        var idPropuesta = this.sharedPreferences.getString("idPropuesta","0")!!

        buttonConfirmarPago.setOnClickListener() {
            // pasar el estado de la propuesta a PAGADO
            // pasar el estado de propuesta a PAGADO
            // TODO: las demas propuestas de la reserva pasan a DESCARTADO
            // TODO: crear el servicio en estado ACTIVO
            // TODO: GRABAR LOS DATOS DE LA TARJETA ENCRIPTADO EN LA BASE
            val parentJob = Job()
            val scope = CoroutineScope(Dispatchers.Default + parentJob)
            scope.launch {
                propuesta = super.getPropuestaById(idPropuesta)
                propuesta.idEstadoPropuesta = EstadoPropuesta.PAGADO.id
                super<PropuestaDao>.update(propuesta)

                reserva = super<ReservaDao>.getReservaById(propuesta.idReserva)
                reserva.idEstadoReserva = EstadoPropuesta.PAGADO.id
                super<ReservaDao>.update(reserva)

            }







            var resultadoMensajeScreen = PagoReservaFragmentDirections.actionPagoReservaFragmentToResultadoMensajeFragment(TipoResultadoMensaje.PAGO_REALIZADO)
            v.findNavController().navigate(resultadoMensajeScreen)
        }

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }
}