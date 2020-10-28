package ar.edu.ort.instituto.echeff.fragments.cliente.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.HomeClienteFragmentDirections
import com.google.android.material.snackbar.Snackbar

class ReservasPendientesFragment(private var reservas: MutableList<Reserva>) : Fragment() {

    lateinit var v: View
    lateinit var rvReserva: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaListAdapter: ReservaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_pendientes, container, false)
        rvReserva = v.findViewById(R.id.recyclerViewReservasPendientes)
        return v
    }

    override fun onStart() {
        super.onStart()
        rvReserva.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        rvReserva.layoutManager = linearLayoutManager
        reservaListAdapter = ReservaListAdapter(reservas, requireContext()) { position -> onItemClick(position) }
        rvReserva.adapter = reservaListAdapter
    }

    private fun onItemClick(position: Int) {
        Snackbar.make(
            v,
            "ID de la reserva pendiente: " + reservas[position].id,
            Snackbar.LENGTH_SHORT
        )
            .show()
        //v.findNavController().navigate(HomeClienteFragmentDirections.actionHomeClienteFragmentToConfirmacionReservaFragment2());
    }

}