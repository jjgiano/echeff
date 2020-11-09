package ar.edu.ort.instituto.echeff.fragments.chef.home

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
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleServicioFragment
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import ar.edu.ort.instituto.echeff.utils.GlideApp


class DetalleServicioFragment : Fragment(), StorageReferenceUtiles {

    lateinit var v: View
    private lateinit var viewModel: ViewModelDetalleServicioFragment

    lateinit var id: TextView
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var tipoServicio: TextView
    lateinit var tipoCocina: TextView
    lateinit var tieneHorno: TextView
    lateinit var direccion: TextView
    lateinit var horario : TextView
    lateinit var cliente: Cliente
    lateinit var imagenCliente : ImageView

    var cargar : Boolean = false
    lateinit var servicio: Servicio
    var reserva: Reserva = Reserva()
    //los input de la propuesta
    lateinit var text_Snack: TextView
    lateinit var text_Entrada: TextView
    lateinit var text_PlatoPricipal: TextView
    lateinit var text_Postre: TextView
    lateinit var text_Adicional: TextView
    lateinit var text_Total: TextView
    var propuesta: Propuesta = Propuesta()
    private lateinit var btn_VolverAHome: Button
    private lateinit var btn_FinalizarServicio: Button

    lateinit var titulo : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle_servicio, container, false)
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        tipoServicio = v.findViewById(R.id.text_DatoTipoServicio)
        tipoCocina = v.findViewById(R.id.text_DatoTipoCocina)
        tieneHorno = v.findViewById(R.id.text_DatoTieneHorno)
        direccion = v.findViewById(R.id.text_DatoDireccion)
        imagenCliente = v.findViewById(R.id.imageViewChef)
        horario = v.findViewById(R.id.tx_DatoHora)

        //Formulario Propuesta
        text_Snack = v.findViewById(R.id.tv_Snack)
        text_Entrada = v.findViewById(R.id.tv_Entrada)
        text_PlatoPricipal = v.findViewById(R.id.tv_PlatoPrincipal)
        text_Postre = v.findViewById(R.id.tv_Postre)
        text_Adicional = v.findViewById(R.id.tv_Adicionales)
        text_Total = v.findViewById(R.id.tv_Total)
        btn_VolverAHome = v.findViewById(R.id.btn_VolveAlHome)
        btn_FinalizarServicio = v.findViewById(R.id.btn_FinalizarServicio)

        titulo = v.findViewById(R.id.text_TituloPropuesta)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelDetalleServicioFragment::class.java)


        viewModel.buscar.observe(viewLifecycleOwner, Observer { result ->
            cargar = result
        })
        viewModel.propuesta.observe(viewLifecycleOwner, Observer { result ->
            propuesta = result
            llenarDatosPropuesta()
        })
        viewModel.reserva.observe(viewLifecycleOwner, Observer { result ->
            reserva = result

        })
        viewModel.cliente.observe(viewLifecycleOwner, Observer {
            cliente = it
            llenarDatosReserva()

        })
    }


    override fun onStart() {
        super.onStart()

        servicio = DetalleServicioFragmentArgs.fromBundle(requireArguments()).servicioArg

        viewModel.setBuscar(servicio)

        if (servicio.idEstadoServicio == EstadoServicio.FINALIZADO.id) {
            btn_FinalizarServicio.setVisibility(View.INVISIBLE);
            titulo.text = "Servicios Realizados"
        } else {
            btn_VolverAHome.setVisibility(View.INVISIBLE);
        }

        btn_VolverAHome.setOnClickListener {
            val action =
                DetalleServicioFragmentDirections.actionDetalleServicioFragmentToHomeChefFragment()
            v.findNavController().navigate(action)
        }

        btn_FinalizarServicio.setOnClickListener {

            viewModel.pasarAFinalizado(servicio)
            viewModel.pasarAFinalizadoReserva(reserva)
            viewModel.pasarAFinalizadoPropuesta(propuesta)

            val action =
                DetalleServicioFragmentDirections.actionDetalleServicioFragmentToHomeChefFragment()
            v.findNavController().navigate(action)
        }


    }


    fun llenarDatosPropuesta() {

        text_Snack.text =  propuesta.snack
        text_Entrada.text = propuesta.entrada
        text_PlatoPricipal.text = propuesta.plato
        text_Postre.text = propuesta.postre
        text_Adicional.text = propuesta.adicional
        text_Total.text = (propuesta.total*reserva.comensales).toString()


    }

    fun llenarDatosReserva() {

        var url = String()
        url = cliente.urlFoto



        GlideApp.with(this)
            .load(buscarReferencia(url))
            .into(imagenCliente)

        usuario.text = cliente.nombre
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        tipoServicio.text = reserva.tipoServicio
        direccion.text = reserva.direccion
        horario.text = reserva.fecha +" a las " + reserva.hora
        if (reserva.tieneHorno.equals(true)) {
            tieneHorno.text = "Si"
        } else {
            tieneHorno.text = "No"
        }

        tipoCocina.text = reserva.tipoCocina

    }
}


