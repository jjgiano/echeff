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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelVistaServiciosFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.google.android.material.snackbar.Snackbar


class VistaServiciosFragment : Fragment(){


    lateinit var v: View
    private lateinit var viewModel: ViewModelVistaServiciosFragment


    var cargado : Boolean = false

    lateinit var servicio: Servicio
    lateinit var reserva: Reserva

    lateinit var textViewMisPropuestas: TextView
    lateinit var buttonTengoUnProblema: Button

    lateinit var textViewTituloServiciosARealizar: TextView
    lateinit var textViewTituloServiciosRealizados: TextView

    lateinit var rvServiciosARealizar: RecyclerView
    lateinit var rvServiciosRealizados: RecyclerView

    var listaServiciosPendientes : MutableList<Servicio> = ArrayList<Servicio>()
    var listaServiciosFinalizados : MutableList<Servicio> = ArrayList<Servicio>()
    var reservasARealizar: MutableList<Reserva> = ArrayList<Reserva>()
    var reservasRealizados: MutableList<Reserva> = ArrayList<Reserva>()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelVistaServiciosFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.listaLiveData.observe(viewLifecycleOwner, Observer { result ->

            reservasARealizar= result
            rvServiciosARealizar.setHasFixedSize(true)
            rvServiciosARealizar.layoutManager = LinearLayoutManager(context)
            rvServiciosARealizar.adapter = ReservaListAdapter(reservasARealizar, requireContext()){
                    position -> onItemAConfirmarClick(position)
            }
        })
        viewModel.serviciosPendientes.observe(viewLifecycleOwner, Observer { result ->
            listaServiciosPendientes = result
        })

        viewModel.listaLiveDataFinalizados.observe(viewLifecycleOwner, Observer { result ->

            reservasRealizados= result
            rvServiciosRealizados.setHasFixedSize(true)
            rvServiciosRealizados.layoutManager = LinearLayoutManager(context)
            rvServiciosRealizados.adapter = ReservaListAdapter(reservasRealizados, requireContext()){
                    position -> onItemFinalizadosClick(position)
            }
        })

        viewModel.serviciosRealizados.observe(viewLifecycleOwner, Observer { result ->
            listaServiciosFinalizados = result
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_vista_servicios_chef, container, false)

        textViewMisPropuestas = v.findViewById(R.id.textViewMisPropuestas)
        buttonTengoUnProblema = v.findViewById(R.id.buttonTengoUnProblema)

        textViewTituloServiciosARealizar = v.findViewById(R.id.textViewTituloPropuestasAConfirmar)
        textViewTituloServiciosRealizados = v.findViewById(R.id.textViewTituloPropuestasConfirmadas)


        rvServiciosARealizar = v.findViewById(R.id.rvPropuestasAConfirmar)
        rvServiciosRealizados = v.findViewById(R.id.rvPropuestasConfirmadas)

        return v
    }


    override fun onStart() {
        super.onStart()
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor, Context.MODE_PRIVATE)
        val idUsuario : String  = sharedPref.getString("userId","Vacio")!!

        viewModel.setcargar(idUsuario)

        buttonTengoUnProblema.setOnClickListener {
             var mesaAyudaScreen = VistaServiciosFragmentDirections.actionVistaPropuestasFragmentToMesaAyudaFragment2(true)
            v.findNavController().navigate(mesaAyudaScreen)
        }

    }

    private fun onItemAConfirmarClick(position : Int){
        var servicio = Servicio()
        servicio=buscarServicio(listaServiciosPendientes,reservasARealizar[position].id)

        val iraservicio = VistaServiciosFragmentDirections.actionVistaPropuestasFragmentToDetalleServicioFragment(servicio)
        v.findNavController().navigate(iraservicio);

    }

    private fun onItemFinalizadosClick(position : Int){
        servicio=buscarServicio(listaServiciosFinalizados,reservasRealizados[position].id)

        val iraservicio = VistaServiciosFragmentDirections.actionVistaPropuestasFragmentToDetalleServicioFragment(servicio)
        v.findNavController().navigate(iraservicio);

    }

    private fun buscarServicio(servicios : MutableList<Servicio>, idReserva: String) : Servicio {
        var servicio =Servicio()
        for (item in servicios) {
            if (item.idReserva.equals(idReserva)) {
                servicio = item
            }
        }
        return servicio
    }

}