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

        reservas.add(Reserva("1", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2, EstadoReserva.NUEVO.id))
        reservas.add(Reserva("5", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.CONFIRMADA.id))
        reservas.add(Reserva("9", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.PENDIENTE.id))
        reservas.add(Reserva("9", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.ACONFIRMAR.id))
        reservas.add(Reserva("9", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.PAGADA.id))

        propuestas.add(Propuesta(1,"snack1", "entrada1", "Nombre plato 1", "postre1", "adicional1", 100.1, 1, "1", "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/image%2025.png?alt=media&token=32ea1268-77ad-4b91-af93-c9ce4d28a059"))
        propuestas.add(Propuesta(2,"snack2", "entrada2", "Nombre plato 2", "postre2", "adicional2", 100.2, 1, "2", "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/image%2025.png?alt=media&token=32ea1268-77ad-4b91-af93-c9ce4d28a059"))
        propuestas.add(Propuesta(3,"snack3", "entrada3", "Nombre plato 3", "postre3", "adicional3", 100.3, 1, "3", "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/image%2025.png?alt=media&token=32ea1268-77ad-4b91-af93-c9ce4d28a059"))
        propuestas.add(Propuesta(4,"snack4", "entrada4", "Nombre plato 4", "postre4", "adicional4", 100.4, 1, "4","https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/image%2025.png?alt=media&token=32ea1268-77ad-4b91-af93-c9ce4d28a059"))
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