package ar.edu.ort.instituto.echeff.fragments.chef

import android.net.sip.SipSession
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmadasFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasDisponiblesFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelHomeChefFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeChefFragment : Fragment() {

    lateinit var v: View
    lateinit var nombreChef: TextView
    private lateinit var viewModel: ViewModelHomeChefFragment
    var cargado : Boolean = false

    //La lista para el recicleView
    var reservas : MutableList<Reserva> = ArrayList<Reserva>()
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout


    //Boton para ver Propuestas
    lateinit var btn_VerProuestas : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_chef, container, false)

        tabLayout = v.findViewById(R.id.TabLayout)

        viewPager = v.findViewById(R.id.viewpage)

        btn_VerProuestas = v.findViewById(R.id.btn_VerPropuetasChef)

        nombreChef = v.findViewById(R.id.text_NombreChef)

        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelHomeChefFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.listaLiveData.observe(viewLifecycleOwner, Observer { result ->

            reservas = result

            viewPager.setAdapter(createCardAdapter())
            // viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Dispobibles"
                    1 -> tab.text = "A Confirmar"
                    2 -> tab.text = "Confirmadas"
                    else -> tab.text = "undefined"
                }
            }).attach()


        })
    }


    override fun onStart() {
        super.onStart()

        viewModel.setcargar()

        //Seteo el nombre del chef
        nombreChef.text = "Hola Chef,"  //Hay que agtregar el nombre del Usuario



        //Boton de Navegacion
        btn_VerProuestas.setOnClickListener{
            val action = HomeChefFragmentDirections.actionHomeChefFragmentToVistaPropuestasFragment()
            v.findNavController().navigate(action)
        }

    }

    private fun createCardAdapter(): HomeChefFragment.ViewPagerAdapter? {
        return HomeChefFragment.ViewPagerAdapter(requireActivity(), reservas)
    }

    private fun onItemClick(position : Int){
        val irareserva = HomeChefFragmentDirections.actionHomeChefFragmentToDetalleReservaFragment(reservas[position])
        v.findNavController().navigate(irareserva);
    }



    class ViewPagerAdapter(fragmentActivity: FragmentActivity, val reservas : MutableList<Reserva>) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> ReservasDisponiblesFragment(reservas)
                1 -> ReservasConfirmarFragment(reservas)
                2 -> ReservasConfirmadasFragment(reservas)

                else -> ReservasDisponiblesFragment(reservas)
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


