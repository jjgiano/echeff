package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.ReservaDao
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelPagoReservaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import java.util.*

class PagoReservaFragment : Fragment(), PropuestaDao, ReservaDao {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var propuesta: Propuesta
    lateinit var reserva: Reserva

    lateinit var v: View
    lateinit var textViewImporteTotal: TextView
    lateinit var textViewImportePorComensal: TextView
    lateinit var txtNombreTarjeta: EditText
    lateinit var txtNumeroTarjeta: EditText
    lateinit var txtVencimientoTarjeta: EditText
    lateinit var txtCsvTarjeta: EditText
    lateinit var buttonConfirmarPago: Button
    lateinit var cbxGuardarTarjeta: CheckBox

    private lateinit var viewModelPagoReserva: ViewModelPagoReservaFragment

    var formTarjetaIsValid: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pago_reserva, container, false)
        textViewImporteTotal = v.findViewById(R.id.textViewImporteTotal)
        textViewImportePorComensal = v.findViewById(R.id.textViewImportePorComensal)
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

        viewModelPagoReserva.reserva.observe(viewLifecycleOwner, Observer { reserva ->
            this.reserva = reserva
            var cantTotal = propuesta.total * reserva.comensales
            textViewImportePorComensal.text = getString(R.string.importeTotal, cantTotal)
        })

        viewModelPagoReserva.propuesta.observe(viewLifecycleOwner, Observer { propuesta ->
            this.propuesta = propuesta
            textViewImporteTotal.text = getString(R.string.importePorComensal, propuesta.total)
            viewModelPagoReserva.getReserva(propuesta.idReserva)
        })

    }

    override fun onStart() {
        super.onStart()

        this.setSharedPreferences()
        var idPropuesta = this.sharedPreferences.getString("idPropuesta", "0")!!
        //todo obtener el idi del usuario
        var idUsuario = "1"
        changStatebutton(buttonConfirmarPago, formTarjetaIsValid)
        viewModelPagoReserva.getTarjeta(idUsuario)
        viewModelPagoReserva.getPropuesta(idPropuesta)

        //Todo si ocurre un error deberia tirar un mensaje de error
        validate();
        buttonConfirmarPago.setOnClickListener() {
            // TODO: las demas propuestas de la reserva pasan a DESCARTADO

            propuesta.idEstadoPropuesta = EstadoPropuesta.PAGADO.id
            viewModelPagoReserva.actualizarPropuesta(propuesta)

            reserva.idEstadoReserva = EstadoReserva.PAGADO.id
            viewModelPagoReserva.actualizarReserva(reserva)


            val tarjetaParaPagar = Tarjeta(
                idUsuario,
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


    fun validate() {
        txtCsvTarjeta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newInput: Editable?) {
                val content = newInput.toString()
                formTarjetaIsValid = content.isNotEmpty() && content.length == 3
                changStatebutton(buttonConfirmarPago, formTarjetaIsValid)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

    }


    fun changStatebutton(button: Button, enable: Boolean) {
        button.isEnabled = enable
        val backgroundColor = if(enable) R.color.orange80 else R.color.gray50
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColor))
    }

}


