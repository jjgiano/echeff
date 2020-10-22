package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.home.DetalleReservaFragmentArgs
import ar.edu.ort.instituto.echeff.fragments.chef.home.DetalleReservaFragmentDirections


class DetalleReservaFragment : Fragment() {

    lateinit var v :View
    lateinit var id: TextView
    lateinit var fecha: TextView
    lateinit var hora: TextView
    lateinit var direccion: TextView
    lateinit var tipoCocina: TextView
    lateinit var tieneHorno: CheckBox
    lateinit var comensales: TextView
    lateinit var estiloCocina: TextView
    lateinit var tipoServicio : TextView
    lateinit var notas: TextView
    lateinit var idUsuario: TextView
    lateinit var reserva : Reserva
    private lateinit var btn_ArmaPropuesta : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle_reserva, container, false)
        fecha = v.findViewById(R.id.text_DatoFecha)
        hora = v.findViewById(R.id.text_DatoHora)
        direccion = v.findViewById(R.id.text_DatoUsuario)
        tipoCocina = v.findViewById(R.id.text_DatoTipoCocina)
        tieneHorno = v.findViewById(R.id.checkBox_TieneHorno)
        comensales = v.findViewById(R.id.text_DatoComensales)
        estiloCocina = v.findViewById(R.id.text_DatoEstiloCocina)
        tipoServicio = v.findViewById(R.id.text_DatoTipoServicio)
        notas = v.findViewById(R.id.text_DatoNotas)
        btn_ArmaPropuesta = v.findViewById(R.id.btn_ArmaPropuesta)
        return v
    }

    override fun onStart() {
        super.onStart()
        reserva = DetalleReservaFragmentArgs.fromBundle(requireArguments()).argReserva
        fecha.text = reserva.fecha
        hora.text = reserva.hora
        direccion.text = reserva.direccion
        tipoCocina.text = reserva.tipoCocina

        if (reserva.tieneHorno.equals("Si"))
            tieneHorno.setChecked(true)
        else
            tieneHorno.setChecked(false)

        comensales.text =  reserva.comensales.toString()
        estiloCocina.text = reserva.estiloCocina
        tipoServicio.text = reserva.tipoServicio
        notas.text = reserva.notas

        /*Glide.with(this)
            .load(pelota.imagen)
            .into(imagen);
           */

        btn_ArmaPropuesta.setOnClickListener {
            val action =
                DetalleReservaFragmentDirections.actionDetalleReservaFragmentToFormularioPropuestaFragment(
                    reserva
                )
            v.findNavController().navigate(action)
        }
    }


}