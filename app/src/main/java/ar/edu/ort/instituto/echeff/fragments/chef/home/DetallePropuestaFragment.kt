package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetallePropuestaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities


class DetallePropuestaFragment : Fragment() {

    lateinit var v: View
    private lateinit var viewModel: ViewModelDetallePropuestaFragment

    var propuestasList : MutableList<Propuesta> = ArrayList<Propuesta>()



    lateinit var id: TextView
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var reserva: Reserva
    lateinit var servicio: TextView

    var cargar : Boolean = false


    //los input de la propuesta
    lateinit var text_Snack: TextView
    lateinit var text_Entrada: TextView
    lateinit var text_PlatoPricipal: TextView
    lateinit var text_Postre: TextView
    lateinit var text_Adicional: TextView
    lateinit var text_Total: TextView
    lateinit var propuesta: Propuesta
    private lateinit var btn_VolverAHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle_propuesta, container, false)
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        servicio = v.findViewById(R.id.text_DatoTipoServicio)


        //Formulario Propuesta
        text_Snack = v.findViewById(R.id.tv_Snack)
        text_Entrada = v.findViewById(R.id.tv_Entrada)
        text_PlatoPricipal = v.findViewById(R.id.tv_PlatoPrincipal)
        text_Postre = v.findViewById(R.id.tv_Postre)
        text_Adicional = v.findViewById(R.id.tv_Adicionales)
        text_Total = v.findViewById(R.id.tv_Total)
        btn_VolverAHome = v.findViewById(R.id.btn_VolveAlHome)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelDetallePropuestaFragment::class.java)

        viewModel.buscar.observe(viewLifecycleOwner, Observer { result ->

            cargar = result

        })

        viewModel.ListDataPropuesta.observe(viewLifecycleOwner, Observer { result ->

            propuestasList= result

            propuesta = buscarPropuesta(propuestasList,reserva.id)

            llenarDatosPropuesta(propuesta)

        })
    }


    override fun onStart() {
        super.onStart()
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor, Context.MODE_PRIVATE)
        val idUsuario : String  = sharedPref.getString("userId","Vacio")!!

        viewModel.setBuscar(idUsuario)

        reserva = DetallePropuestaFragmentArgs.fromBundle(requireArguments()).argReserva
        //lleno los datos de la reserva
        usuario.text = reserva.idUsuario.toString() //Hay que buscar el Usuario
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio



        btn_VolverAHome.setOnClickListener {
            val action =
                DetallePropuestaFragmentDirections.actionDetallePropuestaFragmentToHomeChefFragment()
            v.findNavController().navigate(action)
        }
    }

    fun buscarPropuesta(lista : MutableList<Propuesta>,idReserva : String) : Propuesta {
        var dato : Propuesta = Propuesta()

        for (item in lista) {
            if (item.idReserva.equals(idReserva)) {
                dato = item
            }
        }

        return dato
    }

    fun llenarDatosPropuesta(propuesta : Propuesta) {

        text_Snack.text =  propuesta.snack
        text_Entrada.text = propuesta.entrada
        text_PlatoPricipal.text = propuesta.plato
        text_Postre.text = propuesta.postre
        text_Adicional.text = propuesta.adicional
        text_Total.text = propuesta.total.toString()

    }
}


