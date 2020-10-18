package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VistaReservasFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewMisReservas: TextView
    lateinit var buttonTengoUnProblema: Button

    lateinit var textViewTituloReservasAConfirmar: TextView
    lateinit var textViewTituloReservasConfirmadas: TextView
    lateinit var textViewTituloReservasFinalizadas: TextView

    lateinit var rvReservasAConfirmar: RecyclerView
    lateinit var rvReservasConfirmadas: RecyclerView
    lateinit var rvReservasFinalizadas: RecyclerView

    var reservasAConfirmar: MutableList<Reserva> = ArrayList()
    var reservasConfirmadas: MutableList<Reserva> = ArrayList()
    var reservasFinalizadas: MutableList<Reserva> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservasAConfirmar.add(Reserva("1", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))
        reservasAConfirmar.add(Reserva("2", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))

        reservasConfirmadas.add(Reserva("5", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))
        reservasConfirmadas.add(Reserva("6", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))

        reservasFinalizadas.add(Reserva("9", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))
        reservasFinalizadas.add(Reserva("10", "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2,1))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_vista_reservas, container, false)

        textViewMisReservas = v.findViewById(R.id.textViewMisReservas)
        buttonTengoUnProblema = v.findViewById(R.id.buttonTengoUnProblema)

        textViewTituloReservasAConfirmar = v.findViewById(R.id.textViewTituloReservasAConfirmar)
        textViewTituloReservasConfirmadas = v.findViewById(R.id.textViewTituloReservasConfirmadas)
        textViewTituloReservasFinalizadas = v.findViewById(R.id.textViewTituloReservasFinalizadas)

        rvReservasAConfirmar = v.findViewById(R.id.rvReservasAConfirmar)
        rvReservasConfirmadas = v.findViewById(R.id.rvReservasConfirmadas)
        rvReservasFinalizadas = v.findViewById(R.id.rvReservasFinalizadas)

        return v
    }

    override fun onStart() {
        super.onStart()
        rvReservasAConfirmar.setHasFixedSize(true)
        rvReservasAConfirmar.layoutManager = LinearLayoutManager(context)
        rvReservasAConfirmar.adapter = VistaReservasAdapter(reservasAConfirmar, requireContext()){
                position -> onItemAConfirmarClick(position)
        }

        rvReservasConfirmadas.setHasFixedSize(true)
        rvReservasConfirmadas.layoutManager = LinearLayoutManager(context)
        rvReservasConfirmadas.adapter = VistaReservasAdapter(reservasConfirmadas, requireContext()){
                position -> onItemConfirmadasClick(position)
        }

        rvReservasFinalizadas.setHasFixedSize(true)
        rvReservasFinalizadas.layoutManager = LinearLayoutManager(context)
        rvReservasFinalizadas.adapter = VistaReservasAdapter(reservasFinalizadas, requireContext()){
                position -> onItemFinalizadasClick(position)
        }

        buttonTengoUnProblema.setOnClickListener {
            val tengoUnProblemaPage = VistaReservasFragmentDirections.actionVistaReservasFragmentToMesaAyudaFragment2()
            v.findNavController().navigate(tengoUnProblemaPage)
        }

    }

    private fun onItemAConfirmarClick(position : Int){
        val reserva = reservasAConfirmar[position]
        Snackbar.make(v, "ID de la reserva: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemConfirmadasClick(position : Int){
        val reserva = reservasConfirmadas[position]
        Snackbar.make(v, "ID de la reserva: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemFinalizadasClick(position : Int){
        val reserva = reservasFinalizadas[position]
        Snackbar.make(v, "ID de la reserva: " + reserva.id, Snackbar.LENGTH_SHORT).show()
    }

}