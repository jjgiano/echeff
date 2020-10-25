package ar.edu.ort.instituto.echeff.fragments.chef.home

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
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.entities.Servicio
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelVistaServiciosFragment
import com.google.android.material.snackbar.Snackbar


class VistaServiciosFragment : Fragment(){


    lateinit var v: View
    private lateinit var viewModel: ViewModelVistaServiciosFragment
    var cargado : Boolean = false

    lateinit var propuesta: Propuesta
    lateinit var reserva: Reserva

    lateinit var textViewMisPropuestas: TextView
    lateinit var buttonTengoUnProblema: Button

    lateinit var textViewTituloServiciosARealizar: TextView
    lateinit var textViewTituloServiciosRealizados: TextView

    lateinit var rvServiciosARealizar: RecyclerView
    lateinit var rvServiciosRealizados: RecyclerView

    var servicios : MutableList<Servicio> = ArrayList<Servicio>()
    var serviciosARealizar: MutableList<Reserva> = ArrayList<Reserva>()
    var serviciosRealizados: MutableList<Reserva> = ArrayList<Reserva>()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelVistaServiciosFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.listaLiveData.observe(viewLifecycleOwner, Observer { result ->

            serviciosARealizar= result

            rvServiciosARealizar.setHasFixedSize(true)
            rvServiciosARealizar.layoutManager = LinearLayoutManager(context)
            rvServiciosARealizar.adapter = VistaReservasAdapter(serviciosARealizar, requireContext()){
                    position -> onItemAConfirmarClick(position)
            }
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

        viewModel.setcargar()

        rvServiciosRealizados.setHasFixedSize(true)
        rvServiciosRealizados.layoutManager = LinearLayoutManager(context)
        rvServiciosRealizados.adapter = VistaReservasAdapter(serviciosRealizados, requireContext()){
                position -> onItemConfirmadasClick(position)
        }


        buttonTengoUnProblema.setOnClickListener {
             var mesaAyudaScreen =
                 VistaServiciosFragmentDirections.actionVistaPropuestasFragmentToMesaAyudaFragment2()
            v.findNavController().navigate(mesaAyudaScreen)
        }

    }

    private fun onItemAConfirmarClick(position : Int){
        val iraservicio = VistaServiciosFragmentDirections.actionVistaPropuestasFragmentToDetalleServicioFragment(servicios[position])
        v.findNavController().navigate(iraservicio);
        val reserva = serviciosARealizar[position]
        Snackbar.make(v, "ID de la propuesta: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemConfirmadasClick(position : Int){
        val reserva = serviciosRealizados[position]
        Snackbar.make(v, "ID de la propuesta: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }



}