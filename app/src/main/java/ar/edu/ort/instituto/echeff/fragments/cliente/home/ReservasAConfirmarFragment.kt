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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservasAConfirmarFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.google.android.material.snackbar.Snackbar

class ReservasAConfirmarFragment() : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var v: View
    private lateinit var viewModel: ViewModelReservasAConfirmarFragment
    var cargado : Boolean = false
    lateinit var rvReserva: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaListAdapter: ReservaListAdapter
    private var reservas: MutableList<Reserva> = ArrayList<Reserva>()

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
        v = inflater.inflate(R.layout.fragment_reservas_a_confirmar, container, false)
        rvReserva = v.findViewById(R.id.recyclerViewReservasAConfirmar)
        return v
    }

    override fun onStart() {
        super.onStart()
        this.setSharedPreferences()
        val userId = sharedPreferences.getString("userId","0")!!
        viewModel.setCargar(userId)
        rvReserva.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        rvReserva.layoutManager = linearLayoutManager
    }

    private fun onItemClick(position: Int) {
        Snackbar.make(v, "ID de la reserva a confirmar: " + reservas[position].id, Snackbar.LENGTH_SHORT).show()
        //v.findNavController().navigate(HomeClienteFragmentDirections.actionHomeClienteFragmentToConfirmacionReservaFragment2());
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        this.setSharedPreferences()
        val userId = sharedPreferences.getString("userId","0")!!
        viewModel.setCargar(userId)
    }

}