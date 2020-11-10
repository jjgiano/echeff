package ar.edu.ort.instituto.echeff.fragments.cliente.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.VistaReservasFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasFinalizadasFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities

class ReservasFinalizadasFragment() : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var v: View
    private lateinit var viewModel: ViewModelReservasFinalizadasFragment
    lateinit var rvReserva: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaListAdapter: ReservaListAdapter
    private var reservas: MutableList<Reserva> = ArrayList<Reserva>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setSharedPreferences()
        val userId = sharedPreferences.getString("userId","0")!!

        viewModel = ViewModelProvider(super.requireActivity()).get(ViewModelReservasFinalizadasFragment::class.java)

        viewModel.setCargarReservasFinalizadas(userId)
        viewModel.liveDataReservaList.observe(viewLifecycleOwner, Observer { reservasResult ->
            reservas = reservasResult
            rvReserva.setHasFixedSize(true)
            linearLayoutManager = LinearLayoutManager(context)
            rvReserva.layoutManager = linearLayoutManager
            reservaListAdapter = ReservaListAdapter(reservas, super.requireContext()){
                    position -> onItemClick(position)
            }
            rvReserva.adapter = reservaListAdapter
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reservas_finalizadas, container, false)
        rvReserva = v.findViewById(R.id.recyclerViewReservasFinalizadas)
        return v
    }

    override fun onStart() {
        super.onStart()
    }

    private fun onItemClick(position: Int) {
        var reserva = reservas[position]
        v.findNavController().navigate(VistaReservasFragmentDirections.actionVistaReservasFragmentToCalificarServicioFragment(reserva.id));
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        this.setSharedPreferences()
        val userId = sharedPreferences.getString("userId","0")!!
        viewModel.setCargarReservasFinalizadas(userId)
    }

}