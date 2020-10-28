package ar.edu.ort.instituto.echeff.fragments.cliente.home

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
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.HomeClienteFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasAConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasNuevasFragment
import com.google.android.material.snackbar.Snackbar

class ReservasAConfirmarFragment(private var reservas: MutableList<Reserva>) : Fragment() {

    lateinit var v: View
    private lateinit var viewModel: ViewModelReservasAConfirmarFragment
    var cargado : Boolean = false
    lateinit var rvReserva: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaListAdapter: ReservaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(super.requireActivity()).get(ViewModelReservasAConfirmarFragment::class.java)
        viewModel.cargar.observe(viewLifecycleOwner, Observer { flagResult ->
            cargado = flagResult
        })

        viewModel.liveDataList.observe(viewLifecycleOwner, Observer { reservasResult ->
            reservas = reservasResult
            rvReserva.setHasFixedSize(true)
            rvReserva.layoutManager = LinearLayoutManager(context)
            rvReserva.adapter = VistaReservasAdapter(reservas, super.requireContext()){
                    position -> onItemClick(position)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_a_confirmar, container, false)
        rvReserva = v.findViewById(R.id.recyclerViewReservasAConfirmar)
        return v
    }

    override fun onStart() {
        super.onStart()
        // TODO tomar luego el id de usuario logueado, idUsuario = 1
        viewModel.setCargar(1)
        rvReserva.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        rvReserva.layoutManager = linearLayoutManager
        reservaListAdapter = ReservaListAdapter(reservas, requireContext()) { position -> onItemClick(position) }
        rvReserva.adapter = reservaListAdapter
    }

    private fun onItemClick(position: Int) {
        Snackbar.make(
            v,
            "ID de la reserva a confirmar: " + reservas[position].id,
            Snackbar.LENGTH_SHORT
        )
            .show()
        //v.findNavController().navigate(HomeClienteFragmentDirections.actionHomeClienteFragmentToConfirmacionReservaFragment2());
    }

}