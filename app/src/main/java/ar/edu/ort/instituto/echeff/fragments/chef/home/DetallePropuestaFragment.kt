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
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetallePropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasAConfirmarFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DetallePropuestaFragment : Fragment() {

    lateinit var v: View
    private lateinit var viewModel: ViewModelDetallePropuestaFragment
    private lateinit var viewModelDetalleReserva: ViewModelDetalleReservaFragment

    var propuestasList : MutableList<Propuesta> = ArrayList<Propuesta>()
    lateinit var id: TextView
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var reserva: Reserva
    lateinit var servicio: TextView
    lateinit var imagenCliente : ImageView
    lateinit var cliente: Cliente
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
        imagenCliente = v.findViewById(R.id.imageViewChef)


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
        viewModelDetalleReserva.setBuscar(reserva.idUsuario)
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

    fun llenarFichaReserva() {
        //lleno los datos de la reserva
        val storage = FirebaseStorage.getInstance()
        var url = String()
        var ref: StorageReference
        url = cliente.urlFoto

        if (!url.isNotEmpty()) url = "gs://pf2020-echeff.appspot.com/SinFoto.jpg"
        //busco la referencia por el URL
        if (url.startsWith("gs://", 0, true)) {
            ref = storage.getReferenceFromUrl(url)
        } else {
            ref = storage.getReference(url)
        }


        GlideApp.with(this)
            .load(ref)
            .into(imagenCliente)

        usuario.text = cliente.nombre
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio
    }
}


