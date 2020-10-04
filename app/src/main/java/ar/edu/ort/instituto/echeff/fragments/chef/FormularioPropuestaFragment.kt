package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva


class FormularioPropuestaFragment : Fragment() {
    lateinit var v: View

    lateinit var direccion : TextView
    lateinit var comensales :TextView
    lateinit var estilococina :TextView
    lateinit var reserva : Reserva


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_propuesta, container, false)
        direccion = v.findViewById(R.id.text_DatoDireccion)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)

        return v
    }
    override fun onStart() {
        super.onStart()
        reserva = FormularioPropuestaFragmentArgs.fromBundle(requireArguments()).formularioPropuestaArg
        direccion.text = reserva.direccion
        comensales.text = reserva.comensales.toString() + " Comensales"
        estilococina.text = reserva.estiloCocina



    }
}