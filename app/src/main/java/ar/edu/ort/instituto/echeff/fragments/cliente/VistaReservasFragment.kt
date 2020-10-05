package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

    var reservasAConfirmar: MutableList<Reserva> = ArrayList<Reserva>()
    var reservasConfirmadas: MutableList<Reserva> = ArrayList<Reserva>()
    var reservasFinalizadas: MutableList<Reserva> = ArrayList<Reserva>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservasAConfirmar.add(Reserva(1, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", 1, "Mediterranea", 2))
        reservasAConfirmar.add(Reserva(2, "02/12/2019", "14:02", "Calle falsa 123, CABA", "Electrica", 2, "Marina", 2))
        reservasAConfirmar.add(Reserva(3, "03/12/2019", "14:03", "Calle falsa 123, CABA", "Induccion", 3, "Montaña", 2))
        reservasAConfirmar.add(Reserva(4, "04/12/2019", "14:04", "Calle falsa 123, CABA", "Horno de barro", 4, "Polar", 2))

        reservasConfirmadas.add(Reserva(5, "05/12/2019", "14:05", "Calle falsa 123, CABA", "Tradicional", 5, "Mediterranea", 2))
        reservasConfirmadas.add(Reserva(6, "06/12/2019", "14:06", "Calle falsa 123, CABA", "Electrica", 6, "Marina", 2))
        reservasConfirmadas.add(Reserva(7, "07/12/2019", "14:07", "Calle falsa 123, CABA", "Induccion", 7, "Montaña", 2))
        reservasConfirmadas.add(Reserva(8, "08/12/2019", "14:08", "Calle falsa 123, CABA", "Horno de barro", 8, "Polar", 2))

        reservasFinalizadas.add(Reserva(9, "09/12/2019", "14:09", "Calle falsa 123, CABA", "Tradicional", 9, "Mediterranea", 2))
        reservasFinalizadas.add(Reserva(10, "10/12/2019", "14:10", "Calle falsa 123, CABA", "Electrica", 10, "Marina", 2))
        reservasFinalizadas.add(Reserva(11, "11/12/2019", "14:11", "Calle falsa 123, CABA", "Induccion", 11, "Montaña", 2))
        reservasFinalizadas.add(Reserva(12, "12/12/2019", "14:12", "Calle falsa 123, CABA", "Horno de barro", 12, "Polar", 2))

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
            Snackbar.make(it, "Esto debe ir a la pantalla de la mesa de ayuda", Snackbar.LENGTH_SHORT).show()
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