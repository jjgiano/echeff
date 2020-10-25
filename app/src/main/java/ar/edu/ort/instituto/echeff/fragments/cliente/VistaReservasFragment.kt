package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.EstadoReserva
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelVistaServiciosFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasAConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasFinalizadasFragment
import ar.edu.ort.instituto.echeff.fragments.cliente.home.ReservasNuevasFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VistaReservasFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View



    lateinit var textViewMisReservas: TextView
    lateinit var buttonTengoUnProblema: Button
    lateinit var tabLayoutReservas: TabLayout
    lateinit var viewPager2Reservas: ViewPager2

    var reservas: MutableList<Reserva> = ArrayList<Reserva>()
    var propuestas: MutableList<Propuesta> = ArrayList<Propuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservas.add(Reserva("1", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.NUEVO.id))
        reservas.add(Reserva("2", "01/12/2019", "14:02", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.NUEVO.id))

        reservas.add(Reserva("3", "01/12/2019", "14:02", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.ACONFIRMAR.id))
        reservas.add(Reserva("4", "01/12/2019", "14:02", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.ACONFIRMAR.id))

        reservas.add(Reserva("5", "01/12/2019", "14:03", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.MODIFICADA.id))
        reservas.add(Reserva("6", "01/12/2019", "14:04", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.MODIFICADA.id))

        reservas.add(Reserva("7", "01/12/2019", "14:04", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.PAGADA.id))
        reservas.add(Reserva("8", "01/12/2019", "14:04", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.PAGADA.id))

        reservas.add(Reserva("9", "01/12/2019", "14:05", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.FINALIZADA.id))
        reservas.add(Reserva("0", "01/12/2019", "14:06", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.FINALIZADA.id))
        reservas.add(Reserva("11", "01/12/2019", "14:06", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.FINALIZADA.id))

        reservas.add(Reserva("11", "01/12/2019", "14:06", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.CANCELADO.id))
        reservas.add(Reserva("11", "01/12/2019", "14:06", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,EstadoReserva.CANCELADO.id))

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_vista_reservas, container, false)

        textViewMisReservas = v.findViewById(R.id.textViewMisReservas)
        buttonTengoUnProblema = v.findViewById(R.id.buttonTengoUnProblema)
        tabLayoutReservas = v.findViewById(R.id.tabLayoutReservas)
        viewPager2Reservas = v.findViewById(R.id.viewPager2Reservas)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager2Reservas.setAdapter(VistaReservasFragment.ViewPagerAdapter(requireActivity(), reservas))
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayoutReservas, viewPager2Reservas, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Enviados" // Siguente (en estado = NUEVO)
                1 -> tab.text = "A Confirmar" // A confirmar
                2 -> tab.text = "Finalizadas"
                else -> tab.text = "undefined"
            }
        }).attach()

        buttonTengoUnProblema.setOnClickListener {
            val iniciarReservaPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToFormularioReservaFragment()
            v.findNavController().navigate(iniciarReservaPage)
        }

    }

    private fun onItemReservasClick(position : Int){
        val reserva = reservas[position]
        Snackbar.make(v, "ID de la reserva: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }

     class ViewPagerAdapter(fragmentActivity: FragmentActivity, val reservas: MutableList<Reserva>) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            var nuevas = reservas.filter { it.idEstadoReserva == EstadoReserva.NUEVO.id }
            var aConfirmar = reservas.filter { it.idEstadoReserva == EstadoReserva.ACONFIRMAR.id }
            var finalizadas = reservas.filter { it.idEstadoReserva == EstadoReserva.FINALIZADA.id }
            return when (position) {
                0 -> ReservasNuevasFragment(ArrayList(nuevas))
                1 -> ReservasAConfirmarFragment(ArrayList(aConfirmar))
                2 -> ReservasFinalizadasFragment(ArrayList(finalizadas))
                else -> ReservasFinalizadasFragment(ArrayList())
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 3
        }
    }

}