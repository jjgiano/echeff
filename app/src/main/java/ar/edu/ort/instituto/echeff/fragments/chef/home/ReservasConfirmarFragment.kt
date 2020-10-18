package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.AdapterListReserva
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragmentDirections

class ReservasConfirmarFragment(private var reservaList : MutableList<Reserva>) : Fragment() {

    lateinit var v : View

    //los RecicleViews
    lateinit var rvReserva : RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var reservaAdapterList: AdapterListReserva


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_confirmar, container, false)

        rvReserva = v.findViewById(R.id.recicleView_ReservasConfirmar)

        return v
    }

    override fun onStart() {
        super.onStart()
        rvReserva.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)

        rvReserva.layoutManager = linearLayoutManager

        reservaAdapterList = AdapterListReserva(reservaList,requireContext()){ position -> onItemClick(position)}

        rvReserva.adapter = reservaAdapterList
    }

    private fun onItemClick(position : Int){
        val irareserva = HomeChefFragmentDirections.actionHomeChefFragmentToDetalleReservaFragment(reservaList[position])
        v.findNavController().navigate(irareserva);
    }
}