package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.adapterListReserva
import ar.edu.ort.instituto.echeff.entities.Reserva


class HomeChefFragment : Fragment() {

    lateinit var v: View

    lateinit var rvReservaDisponibles : RecyclerView
    lateinit var rvReservaConfirmar : RecyclerView
    lateinit var rvReservaConfirmadas : RecyclerView

    var reservas : MutableList<Reserva> = ArrayList<Reserva>()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var reservaAdapterList: adapterListReserva

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservas.add(Reserva(1,"20/10/2020","21:00","Nazca 3157","Electrica","Si",4,"Premium","Mediterranea","Nada Agridulce",1))
        reservas.add(Reserva(2,"10/11/2020","13:00","Yatay 723","a Gas","No",8,"Basico","Italiana","No nos gusta el pesto",3))
        reservas.add(Reserva(3,"15/11/2020","12:00","Segurola 2000","A Gas","SI",2,"Especial","Koreana","Algo q no tenga pescado",5))
        reservas.add(Reserva(4,"22/10/2020","20:30","Cespedes 2400","Electrica","si",3,"Basico","Peruana","Poco picante",8))
        reservas.add(Reserva(1,"05/12/2020","22:00","Aguirre 3250","a Gas","No",10,"Especial","Arabe","Nada con Papicra",10))
        reservas.add(Reserva(1,"24/12/2020","21:00","Lozano 3520","Parrilla","No",12,"Premium","Navideña","Solo carne vacuna, no pollo ni cerdo",4))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_chef, container, false)
        rvReservaDisponibles = v.findViewById(R.id.recicleView_reservasDispoibles)
        rvReservaConfirmar = v.findViewById(R.id.recicleView_ReservasConfirmar)
        rvReservaConfirmadas = v.findViewById(R.id.recicleView_reservasDispoibles)
        return v
    }

    override fun onStart() {
        super.onStart()

        rvReservaDisponibles.setHasFixedSize(true)
        rvReservaConfirmar.setHasFixedSize(true)
        rvReservaConfirmadas.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        rvReservaDisponibles.layoutManager = linearLayoutManager

        reservaAdapterList = adapterListReserva(reservas,requireContext()){ position -> onItemClick(position)}

        rvReservaDisponibles.adapter = reservaAdapterList
        rvReservaConfirmar.adapter = reservaAdapterList
        rvReservaConfirmadas.adapter = reservaAdapterList

    }

    private fun onItemClick(position : Int){
        val irareserva = HomeChefFragmentDirections.actionHomeChefFragmentToDetalleReservaFragment(reservas[position])
        v.findNavController().navigate(irareserva);
    }
}