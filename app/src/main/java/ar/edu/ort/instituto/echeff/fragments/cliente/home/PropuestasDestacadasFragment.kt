package ar.edu.ort.instituto.echeff.fragments.cliente.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.PropuestaListAdapter
import ar.edu.ort.instituto.echeff.adapters.ReservaListAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.fragments.cliente.HomeClienteFragmentDirections

class PropuestasDestacadasFragment(private var propuestas: MutableList<Propuesta>) : Fragment() {

    lateinit var v: View
    lateinit var rvPropuesta: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var propuestaListAdapter: PropuestaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_propuestas_destacadas, container, false)
        rvPropuesta = v.findViewById(R.id.recyclerViewPropuestasDestacadas)
        return v
    }

    override fun onStart() {
        super.onStart()
        rvPropuesta.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        rvPropuesta.layoutManager = linearLayoutManager
        propuestaListAdapter = PropuestaListAdapter(propuestas, requireContext()) { position -> onItemClick(position) }
        rvPropuesta.adapter = propuestaListAdapter
    }

    private fun onItemClick(position: Int) {
        v.findNavController().navigate(HomeClienteFragmentDirections.actionHomeClienteFragmentToVistaReservasFragment());
    }

}