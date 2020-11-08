package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetallePropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelFormularioPropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp


class FormularioPropuestaModificacionesFragment : Fragment(), StorageReferenceUtiles {

    lateinit var v: View
    private lateinit var viewModel: ViewModelDetallePropuestaFragment
    private lateinit var viewModelPropuesta: ViewModelFormularioPropuestaFragment
    private lateinit var viewModelReserva: ViewModelReservasConfirmarFragment
    private lateinit var viewModelDetalleReserva : ViewModelDetalleReservaFragment



    var propuestasList: MutableList<Propuesta> = ArrayList<Propuesta>()

    lateinit var id: TextView
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var reserva: Reserva
    lateinit var servicio: TextView
    lateinit var imagenCliente : ImageView
    lateinit var cliente: Cliente
    var cargar: Boolean = false

    //los input de la propuesta
    lateinit var text_Snack: TextView
    lateinit var text_Entrada: TextView
    lateinit var text_PlatoPricipal: TextView
    lateinit var text_Postre: TextView
    lateinit var text_Adicional: TextView
    lateinit var text_Total: TextView
    lateinit var text_Modificaciones: TextView
    lateinit var propuesta: Propuesta

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
        v = inflater.inflate(
            R.layout.fragment_formulario_propuesta_modificaciones,
            container,
            false
        )
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        servicio = v.findViewById(R.id.text_DatoTipoServicio)
        imagenCliente = v.findViewById(R.id.imageViewChef)


        //Formulario Propuesta
        text_Snack = v.findViewById(R.id.editText_Snack)
        text_Entrada = v.findViewById(R.id.editText_Entrada)
        text_PlatoPricipal = v.findViewById(R.id.editText_PlatoPrincipal)
        text_Postre = v.findViewById(R.id.editText_Postre)
        text_Adicional = v.findViewById(R.id.editText_Adicionales)
        text_Total = v.findViewById(R.id.editText_Total)
        text_Modificaciones = v.findViewById(R.id.tv_Modificaciones)
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

        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelDetallePropuestaFragment::class.java)
        viewModelPropuesta =
            ViewModelProvider(requireActivity()).get(ViewModelFormularioPropuestaFragment::class.java)
        viewModelReserva =
            ViewModelProvider(requireActivity()).get(ViewModelReservasConfirmarFragment::class.java)
        viewModelDetalleReserva =
            ViewModelProvider(requireActivity()).get(ViewModelDetalleReservaFragment::class.java)


        viewModelDetalleReserva.cliente.observe(viewLifecycleOwner, Observer { result ->

            cliente = result


            llenarFichaReserva()
        })

        viewModel.buscar.observe(viewLifecycleOwner, Observer { result ->

            cargar = result

        })

        viewModel.ListDataPropuesta.observe(viewLifecycleOwner, Observer { result ->

            propuestasList = result

            propuesta = buscarPropuesta(propuestasList, reserva.id)

            llenarDatosPropuesta(propuesta)

        })
    }


    override fun onStart() {
        super.onStart()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor, Context.MODE_PRIVATE)
        val idUsuario : String  = sharedPref.getString("userId","Vacio")!!

        viewModel.setBuscar(idUsuario)

        reserva =
            FormularioPropuestaModificacionesFragmentArgs.fromBundle(requireArguments()).reservaArg




        btn_Propuesta.setOnClickListener {

            //guardo la propuesta
            //Si se modifico guardo los dato sen la nueva Propuesta
            propuesta.snack = text_Snack.text.toString()
            propuesta.entrada = text_Entrada.text.toString()
            propuesta.plato = text_PlatoPricipal.text.toString()
            propuesta.postre = text_Postre.text.toString()
            propuesta.adicional = text_Adicional.text.toString()
            propuesta.total = text_Total.text.toString().toDouble()
            propuesta.idReserva = reserva.id
            propuesta.idChef = idUsuario
            propuesta.importeTotal = text_Total.text.toString().toDouble() * reserva.comensales

            //modifico en Firebase
            viewModelPropuesta.modificarPropuesta(propuesta)

            //Cambio los botones y blockeo los textos
            btn_EditarPropuesta.setVisibility(View.VISIBLE);
            btn_EnviarPropuesta.setVisibility(View.VISIBLE);
            btn_Propuesta.setVisibility(View.INVISIBLE);
            text_Snack.setFocusable(false)
            text_Entrada.setFocusable(false)
            text_PlatoPricipal.setFocusable(false)
            text_Postre.setFocusable(false)
            text_Adicional.setFocusable(false)
            text_Total.setFocusable(false)
        }
        // Boton de envio de Propuesta
        btn_EnviarPropuesta.setOnClickListener {
            propuesta.idEstadoPropuesta = EstadoPropuesta.ACONFIRMAR.id
            viewModelPropuesta.modificarPropuesta(propuesta)
            viewModelReserva.pasarAConfirmar(reserva)
            val action =
                FormularioPropuestaModificacionesFragmentDirections.actionFormularioPropuestaModificacionesFragmentToResultadoMensajeFragment(TipoResultadoMensaje.MODIFICAR_PROPUESTA, true)
            v.findNavController().navigate(action)
        }

        btn_EditarPropuesta.setOnClickListener {

            //modificar la vista de los botones

            btn_EnviarPropuesta.setVisibility(View.INVISIBLE);
            btn_EditarPropuesta.setVisibility(View.INVISIBLE);
            btn_Propuesta.setVisibility(View.VISIBLE);

            text_Snack.setFocusable(true)
            text_Snack.setClickable(true)
            text_Snack.setEnabled(true)
            text_Entrada.setFocusable(true)
            text_Entrada.setClickable(true)
            text_Entrada.setEnabled(true)
            text_PlatoPricipal.setFocusable(true)
            text_PlatoPricipal.setClickable(true)
            text_PlatoPricipal.setEnabled(true)
            text_Postre.setFocusable(true)
            text_Postre.setClickable(true)
            text_Postre.setEnabled(true)
            text_Adicional.setFocusable(true)
            text_Adicional.setClickable(true)
            text_Adicional.setEnabled(true)
            text_Total.setFocusable(true)
            text_Total.setClickable(true)
            text_Total.setEnabled(true)


        }
    }

    fun buscarPropuesta(lista: MutableList<Propuesta>, idReserva: String): Propuesta {
        var dato: Propuesta = Propuesta()

        for (item in lista) {
            if (item.idReserva.equals(idReserva)) {
                dato = item
            }
        }
        viewModelDetalleReserva.setBuscar(reserva.idUsuario)
        return dato
    }

    fun llenarDatosPropuesta(propuesta: Propuesta) {

        text_Snack.text = propuesta.snack
        text_Entrada.text = propuesta.entrada
        text_PlatoPricipal.text = propuesta.plato
        text_Postre.text = propuesta.postre
        text_Adicional.text = propuesta.adicional
        text_Total.text = propuesta.total.toString()
        text_Modificaciones.text = propuesta.modificaciones

    }

    fun llenarFichaReserva() {

        var url = String()
        url = cliente.urlFoto

        if (!url.isNotEmpty()) url = EcheffUtilities.SIN_FOTO.valor


        GlideApp.with(this)
            .load(buscarReferencia(url))
            .into(imagenCliente)

        usuario.text = cliente.nombre
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio

    }
}


