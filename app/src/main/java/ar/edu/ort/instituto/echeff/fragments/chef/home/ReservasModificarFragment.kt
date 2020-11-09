package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.AdapterListReserva
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasModificarFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities

class ReservasModificarFragment() : Fragment() {

    lateinit var v: View
    private lateinit var viewModel: ViewModelReservasModificarFragment
    var cargado: Boolean = false


    //los RecicleViews
    lateinit var rvReserva: RecyclerView
    lateinit var textoNohay: TextView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var reservaAdapterList: AdapterListReserva

    private var reservaList: MutableList<Reserva> = ArrayList<Reserva>()
    private lateinit var swipeRefres: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelReservasModificarFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.liveDataList.observe(viewLifecycleOwner, Observer { result ->

            reservaList = result

            reservaList.sortBy { it.fecha }

            if (reservaList.size > 0) {
                textoNohay.visibility = View.INVISIBLE
            } else {
                textoNohay.visibility = View.VISIBLE
            }

            reservaAdapterList = AdapterListReserva(reservaList,
                requireContext()) { position -> onItemClick(position) }

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
        swipeRefres = v.findViewById(R.id.SwipeRefresh)
        textoNohay= v.findViewById(R.id.mensajeNoHay)

        textoNohay.visibility = View.VISIBLE
        return v
    }

    override fun onStart() {
        super.onStart()

        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor,
                Context.MODE_PRIVATE)
        val idUsuario: String = sharedPref.getString("userId", "Vacio")!!

        viewModel.setcargar(idUsuario)

        rvReserva.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)

        rvReserva.layoutManager = linearLayoutManager

        //Refresca con el Pull
        swipeRefres.setOnRefreshListener {
            viewModel.setcargar(idUsuario)
            swipeRefres.isRefreshing = false
        }
    }

    private fun onItemClick(position: Int) {
        val irareserva =
            HomeChefFragmentDirections.actionHomeChefFragmentToFormularioPropuestaModificacionesFragment(
                reservaList[position])
        v.findNavController().navigate(irareserva);
    }
}