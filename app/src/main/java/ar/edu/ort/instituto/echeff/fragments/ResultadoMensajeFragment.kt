package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.TipoResultadoMensaje
import ar.edu.ort.instituto.echeff.fragments.cliente.FormularioReservaDosFragmentDirections

class ResultadoMensajeFragment : Fragment() {
    lateinit var v: View
    lateinit var txtPrimerMensaje: TextView
    lateinit var txtSegundoMensaje: TextView
    lateinit var btnVolverHome: Button
    lateinit var imgIconMensaje: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.resultado_mensaje_fragment, container, false)

        txtPrimerMensaje = v.findViewById(R.id.txtPrimerMensaje)
        txtSegundoMensaje = v.findViewById(R.id.txtSegundoMensaje)
        btnVolverHome = v.findViewById(R.id.btnVolverHome)
        imgIconMensaje = v.findViewById(R.id.imgIconMensaje)
        return v;
    }


    override fun onStart() {
        super.onStart()
        setMensajes()
        btnVolverHome.setOnClickListener {
            if (ResultadoMensajeFragmentArgs.fromBundle(requireArguments()).isChef) {
                v.findNavController()
                    .navigate(ResultadoMensajeFragmentDirections.actionResultadoMensajeFragmentToHomeChefFragment())
            } else {
                v.findNavController()
                    .navigate(ResultadoMensajeFragmentDirections.actionResultadoMensajeFragmentToHomeClienteFragment())
            }
        }
    }

    private fun setMensajes() {
        when (ResultadoMensajeFragmentArgs.fromBundle(requireArguments()).tipoMensaje) {
            TipoResultadoMensaje.NUEVA_RESERVA -> {
                txtPrimerMensaje.text = "Se registro tu reserva."
                txtSegundoMensaje.text =
                    "En las proximas horas vas a estar recibiendo las mejores propuestas para vos."
                imgIconMensaje.setImageResource(R.drawable.ic_check_orange_ok)
            }
            TipoResultadoMensaje.MODIFCAR_RESERVA -> {
                txtPrimerMensaje.text = "Se envio la modificacion al Chef."
                txtSegundoMensaje.text = "Debes esperar a que el Chef envie una nueva propuesta."
                imgIconMensaje.setImageResource(R.drawable.ic_message_orange_warn)
            }
            TipoResultadoMensaje.PAGO_REALIZADO -> {
                txtPrimerMensaje.text = "Ya realizaste el pago de la reserva."
                txtSegundoMensaje.text = "Esperemos que disfrutes de la experiencia!"
                imgIconMensaje.setImageResource(R.drawable.ic_check_orange_ok)
            }
            TipoResultadoMensaje.NUEVA_PROPUESTA -> {
                txtPrimerMensaje.text = "Se envio la propuesta al cliente."
                txtSegundoMensaje.text =
                    "Debes esperar a que el cliente acepte o solicite una nueva modificacion."
                imgIconMensaje.setImageResource(R.drawable.ic_check_orange_ok)
            }
            TipoResultadoMensaje.MODIFICAR_PROPUESTA -> {
                txtPrimerMensaje.text = "Se envio la modificacion de la propuesta al cliente."
                txtSegundoMensaje.text =
                    "Debes esperar a que el cliente acepte o solicite una nueva modificacion."
                imgIconMensaje.setImageResource(R.drawable.ic_check_orange_ok)
            }
            TipoResultadoMensaje.NUEVO_MESA_AYUDA -> {
                txtPrimerMensaje.text = "Se envio tu consulta a la mesa de ayuda."
                txtSegundoMensaje.text = "Su consulta va a ser respondida en las proximas horas."
                imgIconMensaje.setImageResource(R.drawable.ic_message_orange_warn)
            }
        }
    }
}