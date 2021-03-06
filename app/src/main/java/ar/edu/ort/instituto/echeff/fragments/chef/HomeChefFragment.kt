package ar.edu.ort.instituto.echeff.fragments.chef

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasModificarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.fragments.chef.home.ReservasDisponiblesFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.getbase.floatingactionbutton.FloatingActionButton

class HomeChefFragment : Fragment() {

    lateinit var v: View
    lateinit var nombreChef: TextView

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var sharedPreferences: SharedPreferences


    //Boton menu
    lateinit var btn_VerServicios: FloatingActionButton
    lateinit var btn_irPerfil: FloatingActionButton
    lateinit var btn_irReportes: FloatingActionButton
    var chef = Chef()

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

        btn_VerServicios = v.findViewById(R.id.btn_VerServicosChef)

        btn_irPerfil = v.findViewById(R.id.fabPerfil)

        btn_irReportes = v.findViewById(R.id.fabReportes)

        nombreChef = v.findViewById(R.id.text_NombreChef)

        return v
    }


    override fun onStart() {
        super.onStart()
        setSharedPreferences()
        val nombreUsuario : String = sharedPreferences.getString("userDisplayName","Nombre No encontrado")!!

        nombreChef.text =  getString(R.string.bienvenida_chef, nombreUsuario)

        viewPager.setAdapter(createCardAdapter())
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Disponibles"
                    1 -> tab.text = "A Confirmar"
                    2 -> tab.text = "Modificar"
                    else -> tab.text = "undefined"
                }
            }).attach()

        //Seteo el nombre del chef
        nombreChef.text =  getString(R.string.bienvenida_chef, nombreUsuario)

        //Boton de Navegacion
        btn_VerServicios.setOnClickListener {
            val action =
                HomeChefFragmentDirections.actionHomeChefFragmentToVistaPropuestasFragment()
            v.findNavController().navigate(action)
        }
        btn_irPerfil.setOnClickListener {
            val action =
                HomeChefFragmentDirections.actionHomeChefFragmentToPerfilChefFragment(chef)
            v.findNavController().navigate(action)
        }
        btn_irReportes.setOnClickListener {
            val action =
                HomeChefFragmentDirections.actionHomeChefFragmentToReportesChefFragment()
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
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

}


