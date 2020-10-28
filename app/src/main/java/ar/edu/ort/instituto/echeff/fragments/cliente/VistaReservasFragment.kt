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
            val iniciarReservaPage = VistaReservasFragmentDirections.actionVistaReservasFragmentToMesaAyudaFragment2(false)
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