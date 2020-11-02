package ar.edu.ort.instituto.echeff.fragments.chef

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasModificarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasDisponiblesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeChefFragment : Fragment() {

    lateinit var v: View
    lateinit var nombreChef: TextView

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var sharedPreferences: SharedPreferences

    //Boton para ver Propuestas
    lateinit var btn_VerProuestas: Button
    lateinit var btn_irPerfil: Button

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

        btn_irPerfil = v.findViewById(R.id.btn_irPerfilChef)

        nombreChef = v.findViewById(R.id.text_NombreChef)

        return v
    }


    override fun onStart() {
        super.onStart()

        setSharedPreferences()
        nombreChef.text = "Hola Chef " + sharedPreferences.getString("userDisplayName", "")

        viewPager.setAdapter(createCardAdapter())
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Dispobibles"
                    1 -> tab.text = "A Confirmar"
                    2 -> tab.text = "Modificar"
                    else -> tab.text = "undefined"
                }
            }).attach()

        //Seteo el nombre del chef
        nombreChef.text = "Hola Chef,"  //Hay que agtregar el nombre del Usuario

        //Boton de Navegacion
        btn_VerProuestas.setOnClickListener {
            val action =
                HomeChefFragmentDirections.actionHomeChefFragmentToVistaPropuestasFragment()
            v.findNavController().navigate(action)
        }
        btn_irPerfil.setOnClickListener {
            val action =
                HomeChefFragmentDirections.actionHomeChefFragmentToPerfilChefFragment()
            v.findNavController().navigate(action)
        }


    }

    private fun createCardAdapter(): HomeChefFragment.ViewPagerAdapter? {
        return HomeChefFragment.ViewPagerAdapter(requireActivity())
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> ReservasDisponiblesFragment()
                1 -> ReservasConfirmarFragment()
                2 -> ReservasModificarFragment()

                else -> ReservasDisponiblesFragment()
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 3
        }
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            "MySharedPref",
            AppCompatActivity.MODE_PRIVATE
        )
    }

}


