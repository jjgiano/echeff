package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelPagoReservaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

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
    lateinit var buttonConfirmarPago: Button
    lateinit var cbxGuardarTarjeta: CheckBox

    private lateinit var viewModelPagoReserva: ViewModelPagoReservaFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pago_reserva, container, false)
        textViewImporteTotal = v.findViewById(R.id.textViewImporteTotal)
        txtNombreTarjeta = v.findViewById(R.id.txtNombreTarjeta)
        txtNumeroTarjeta = v.findViewById(R.id.txtNumeroTarjeta)
        txtVencimientoTarjeta = v.findViewById(R.id.txtVencimientoTarjeta)
        txtCsvTarjeta = v.findViewById(R.id.txtCsvTarjeta)
        buttonConfirmarPago = v.findViewById(R.id.buttonConfirmarPago)
        cbxGuardarTarjeta = v.findViewById(R.id.cbxGuardarTarjeta)

        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelPagoReserva =
            ViewModelProvider(requireActivity()).get(ViewModelPagoReservaFragment::class.java)

        viewModelPagoReserva.tarjetaUsuario.observe(viewLifecycleOwner, Observer { tarjeta ->
            txtNombreTarjeta.setText(decode(tarjeta.nombre))
            txtNumeroTarjeta.setText(decode(tarjeta.numero))
            txtVencimientoTarjeta.setText(decode(tarjeta.vencimiento))
        })

    }

    override fun onStart() {
        super.onStart()
        this.setSharedPreferences()

        var idPropuesta = this.sharedPreferences.getString("idPropuesta", "0")!!

        viewModelPagoReserva.getTarjeta("1")
        //Todo si ocurre un error deberia tirar un mensaje de error
        //todo si no deberia ir a la confimacion del servicio
        buttonConfirmarPago.setOnClickListener() {
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

            //todo obtener el numero de id del usuario
            val tarjetaParaPagar = Tarjeta(
                "1",
                txtNombreTarjeta.text.toString(),
                txtNumeroTarjeta.text.toString(),
                txtVencimientoTarjeta.text.toString(),
                txtCsvTarjeta.text.toString()
            )

            if (cbxGuardarTarjeta.isChecked) {
                store(tarjetaParaPagar)
            }
            var resultadoMensajeScreen =
                PagoReservaFragmentDirections.actionPagoReservaFragmentToResultadoMensajeFragment(
                    TipoResultadoMensaje.PAGO_REALIZADO
                )
            v.findNavController().navigate(resultadoMensajeScreen)
        }

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun store(tarjeta: Tarjeta) {
        val tarjetaEncodeadaAGuardar = Tarjeta(
            tarjeta.idUsuario,
            encode(tarjeta.nombre),
            encode(tarjeta.numero),
            encode(tarjeta.vencimiento),
            encode("superSecret:D")
        )
        viewModelPagoReserva.setTarjeta(tarjetaEncodeadaAGuardar)

    }

    private fun encode(field: String): String {
        return Base64.getEncoder().encodeToString(field.toByteArray());
    }

    private fun decode(fieldEncoded: String): String {
        val decodedBytes = Base64.getDecoder().decode(fieldEncoded);
        return String(decodedBytes)
    }

    private fun buscarTarjetaCliente() {
        // TODO: 11/1/2020 obtener usuario
        viewModelPagoReserva.getTarjeta("1")
    }
}


