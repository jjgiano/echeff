package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R

class PagoReservaFragment : Fragment() {
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

        //Todo si ocurre un error deberia tirar un mensaje de error
        //todo si no deberia ir a la confimacion del servicio
        buttonConfirmarPago.setOnClickListener() {
            var verificarServicioPage =
                PagoReservaFragmentDirections.actionPagoReservaFragmentToConfirmacionReservaFragment2()
            v.findNavController().navigate(verificarServicioPage)
        }
    }
}