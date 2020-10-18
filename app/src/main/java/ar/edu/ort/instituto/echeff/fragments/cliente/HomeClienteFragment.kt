package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.HomeChefFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmadasFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasDisponiblesFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.PropuestasDestacadasFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasAConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasPendientesFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasSiguientesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeClienteFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewSaludoCliente: TextView
    lateinit var textViewReservas: TextView

    lateinit var buttonIniciarReserva: Button
    lateinit var buttonVerMisReservas: Button

    lateinit var tabLayoutReservas: TabLayout

    lateinit var viewPager2Reservas: ViewPager2

    var reservas: MutableList<Reserva> = ArrayList<Reserva>()
    var propuestas: MutableList<Propuesta> = ArrayList<Propuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_cliente, container, false)

        textViewSaludoCliente = v.findViewById(R.id.textViewSaludoCliente)
        textViewReservas = v.findViewById(R.id.textViewReservas)

        tabLayoutReservas = v.findViewById(R.id.tabLayoutReservas)
        viewPager2Reservas = v.findViewById(R.id.viewPager2Reservas)

        buttonIniciarReserva = v.findViewById(R.id.buttonIniciarReserva)
        buttonVerMisReservas = v.findViewById(R.id.buttonVerMisReservas)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager2Reservas.setAdapter(HomeClienteFragment.ViewPagerAdapter(requireActivity(), reservas, propuestas))
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayoutReservas, viewPager2Reservas, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Siguiente"
                1 -> tab.text = "Confirmar"
                2 -> tab.text = "Pendiente"
                3 -> tab.text = "Destacada"
                else -> tab.text = "undefined"
            }
        }).attach()

        buttonIniciarReserva.setOnClickListener {
            val iniciarReservaPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToFormularioReservaFragment()
            v.findNavController().navigate(iniciarReservaPage)
        }

        buttonVerMisReservas.setOnClickListener {
            val verMisReservasPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToVistaReservasFragment()
            v.findNavController().navigate(verMisReservasPage)
        }
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity, val reservas : MutableList<Reserva>, val propuestas : MutableList<Propuesta>) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> ReservasSiguientesFragment(reservas)
                1 -> ReservasAConfirmarFragment(reservas)
                2 -> ReservasPendientesFragment(reservas)
                3 -> PropuestasDestacadasFragment(propuestas)
                else -> PropuestasDestacadasFragment(propuestas)
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 4
        }
    }

}