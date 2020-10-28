package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.AdapterListReserva
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasModificarFragment

class ReservasModificarFragment() : Fragment() {

    lateinit var v : View
    private lateinit var viewModel: ViewModelReservasModificarFragment
    var cargado : Boolean = false

    //los RecicleViews
    lateinit var rvReserva : RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var reservaAdapterList: AdapterListReserva

    private var reservaList : MutableList<Reserva> = ArrayList<Reserva>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelReservasModificarFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.liveDataList.observe(viewLifecycleOwner, Observer { result ->

            reservaList = result

            reservaList.sortBy { it.fecha }

            reservaAdapterList = AdapterListReserva(reservaList,requireContext()){ position -> onItemClick(position)}

            rvReserva.adapter = reservaAdapterList

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_modificar, container, false)

        rvReserva = v.findViewById(R.id.recicleView_ReservasModificar)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.setcargar()

        rvReserva.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)

        rvReserva.layoutManager = linearLayoutManager


    }
    private fun onItemClick(position : Int){
        val irareserva = HomeChefFragmentDirections.actionHomeChefFragmentToFormularioPropuestaModificacionesFragment(reservaList[position])
        v.findNavController().navigate(irareserva);
    }}