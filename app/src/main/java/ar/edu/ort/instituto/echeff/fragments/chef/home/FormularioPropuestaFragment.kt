package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.home.FormularioPropuestaFragmentArgs
import ar.edu.ort.instituto.echeff.fragments.chef.home.FormularioPropuestaFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelFormularioPropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelPropuestasConfirmarFragment
import kotlin.collections.ArrayList


class FormularioPropuestaFragment : Fragment() {
    lateinit var v: View
    private lateinit var viewModelPropuesta: ViewModelFormularioPropuestaFragment
    private lateinit var viewModelReserva: ViewModelPropuestasConfirmarFragment

    var nuevaPropuesta: Propuesta = Propuesta()

    // datos de la reserva
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var reserva: Reserva
    lateinit var servicio: TextView

    //los input de la propuesta
    lateinit var editText_Snack: EditText
    lateinit var editText_Entrada: EditText
    lateinit var editText_PlatoPricipal: EditText
    lateinit var editText_Postre: EditText
    lateinit var editText_Adicional: EditText
    lateinit var editText_Importe: EditText

    //botones de Guardar y Enviar
    lateinit var btn_Propuesta: Button
    lateinit var btn_EditarPropuesta: Button
    lateinit var btn_EnviarPropuesta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_propuesta, container, false)
        //datos de la Tarjeta de reserva
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        servicio = v.findViewById(R.id.text_DatoTipoServicio)


        //Formulario Propuesta
        editText_Snack = v.findViewById(R.id.editText_Snack)
        editText_Entrada = v.findViewById(R.id.editText_Entrada)
        editText_PlatoPricipal = v.findViewById(R.id.editText_PlatoPrincipal)
        editText_Postre = v.findViewById(R.id.editText_Postre)
        editText_Adicional = v.findViewById(R.id.editText_Adicionales)
        editText_Importe = v.findViewById(R.id.editText_Total)

        //Boton de Propuesta
        btn_Propuesta = v.findViewById(R.id.btn_GuardarPropuesta)
        btn_EditarPropuesta = v.findViewById(R.id.btn_EditarPropuesta)
        btn_EnviarPropuesta = v.findViewById(R.id.btn_EnviarPropuesta)

        btn_EnviarPropuesta.setVisibility(View.INVISIBLE);
        btn_EditarPropuesta.setVisibility(View.INVISIBLE);


        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelPropuesta = ViewModelProvider(requireActivity()).get(ViewModelFormularioPropuestaFragment::class.java)
        viewModelReserva = ViewModelProvider(requireActivity()).get(ViewModelPropuestasConfirmarFragment::class.java)
    }

    override fun onStart() {
        super.onStart()
        var modificado: Boolean = false

        //Obtengo la reserva que llega por Argumentos de navegacion
        reserva = FormularioPropuestaFragmentArgs.fromBundle(requireArguments()).formularioPropuestaArg

        //lleno los datos de la reserva
        usuario.text = reserva.idUsuario.toString() //Hay que buscar el Usuario
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio

        //Boton de Guardar la Propuesta y cambiar los botones y blockear los EditText
        btn_Propuesta.setOnClickListener {

            //guardo la propuesta
            //Todo: hay que buscar los IDs que tiene 1.

            //Si se modifico guardo los dato sen la nueva Propuesta
            nuevaPropuesta.snack = editText_Snack.text.toString()
            nuevaPropuesta.entrada = editText_Entrada.text.toString()
            nuevaPropuesta.plato = editText_PlatoPricipal.text.toString()
            nuevaPropuesta.postre = editText_Postre.text.toString()
            nuevaPropuesta.adicional = editText_Adicional.text.toString()
            nuevaPropuesta.total = editText_Importe.text.toString().toDouble()
            nuevaPropuesta.idReserva = reserva.id
            nuevaPropuesta.idChef = "1"

            //Guardo o modifico en Firebase
            if (modificado) {
                viewModelPropuesta.modificarPropuesta(nuevaPropuesta)
            } else {
                viewModelPropuesta.guardarPropuesta(nuevaPropuesta)
            }

            //Cambio los botones y blockeo los textos
            btn_EditarPropuesta.setVisibility(View.VISIBLE);
            btn_EnviarPropuesta.setVisibility(View.VISIBLE);
            btn_Propuesta.setVisibility(View.INVISIBLE);
            editText_Snack.setFocusable(false)
            editText_Entrada.setFocusable(false)
            editText_PlatoPricipal.setFocusable(false)
            editText_Postre.setFocusable(false)
            editText_Adicional.setFocusable(false)
            editText_Importe.setFocusable(false)
        }

        // Boton de envio de Propuesta
        btn_EnviarPropuesta.setOnClickListener {
            viewModelReserva.pasarAConfirmar(reserva)
            val action =
                FormularioPropuestaFragmentDirections.actionFormularioPropuestaFragmentToHomeChefFragment()
            v.findNavController().navigate(action)
        }

        btn_EditarPropuesta.setOnClickListener {
            modificado = true
            //modificar la vista de los botones

            btn_EnviarPropuesta.setVisibility(View.INVISIBLE);
            btn_EditarPropuesta.setVisibility(View.INVISIBLE);
            btn_Propuesta.setVisibility(View.VISIBLE);

            editText_Snack.setFocusable(true)
            editText_Snack.setClickable(true)

            editText_Entrada.setFocusable(true)
            editText_PlatoPricipal.setFocusable(true)
            editText_Postre.setFocusable(true)
            editText_Adicional.setFocusable(true)
            editText_Importe.setFocusable(true)
        }
    }
}