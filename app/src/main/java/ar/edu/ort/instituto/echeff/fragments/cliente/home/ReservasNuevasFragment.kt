package ar.edu.ort.instituto.echeff.fragments.cliente.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasNuevasFragment
import com.google.android.material.snackbar.Snackbar

class ReservasNuevasFragment(private var reservas: MutableList<Reserva>) : Fragment() {

    lateinit var v: View
    private lateinit var viewModel: ViewModelReservasNuevasFragment
    var cargado : Boolean = false
    lateinit var rvReserva: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaListAdapter: ReservaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(super.requireActivity()).get(ViewModelReservasNuevasFragment::class.java)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_nuevas, container, false)
        rvReserva = v.findViewById(R.id.recyclerViewReservasNuevas)
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
        Snackbar.make(v, "ID de la reserva nueva: " + reservas[position].id, Snackbar.LENGTH_SHORT).show()
        //v.findNavController().navigate(VistaReservasFragmentDirections.actionVistaReservasFragmentToMesaAyudaFragment2());
    }

}