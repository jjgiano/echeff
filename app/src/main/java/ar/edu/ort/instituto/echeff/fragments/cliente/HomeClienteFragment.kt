package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.Intent
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
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeClienteFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewSaludoCliente: TextView
    lateinit var textViewProximasReservas: TextView
    lateinit var textViewReservasAConfirmar: TextView
    lateinit var textViewReservasPendientes: TextView
    lateinit var textViewPropuestasDestacadas: TextView

    lateinit var buttonIniciarReserva: Button
    lateinit var buttonVerMisReservas: Button

    lateinit var rvProximaReserva: RecyclerView
    lateinit var rvReservasAConfirmar: RecyclerView
    lateinit var rvReservasPendientes: RecyclerView
    lateinit var rvPropuestasDestacadas: RecyclerView

    var reservasProximas: MutableList<Reserva> = ArrayList<Reserva>()
    var reservasAConfirmar: MutableList<Reserva> = ArrayList<Reserva>()
    var reservasPendientes: MutableList<Reserva> = ArrayList<Reserva>()
    var propuestasDestacadas: MutableList<Propuesta> = ArrayList<Propuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservasProximas.add(Reserva(1, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasProximas.add(Reserva(2, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasProximas.add(Reserva(3, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasProximas.add(Reserva(4, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))

        reservasAConfirmar.add(Reserva(5, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasAConfirmar.add(Reserva(6, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasAConfirmar.add(Reserva(7, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasAConfirmar.add(Reserva(8, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))

        reservasPendientes.add(Reserva(9, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasPendientes.add(Reserva(10, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasPendientes.add(Reserva(11, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))
        reservasPendientes.add(Reserva(12, "01/12/2019", "14:01", "Calle falsa 123, CABA", "Tradicional", "Induccion", 1,"Presencial TS", "Mediterranea", "Notas sobre la reserva", 2))

        propuestasDestacadas.add(Propuesta(1,"snack1", "entrada1", "plato1", "postre1", "adicional1", 100.1, 1, 1))
        propuestasDestacadas.add(Propuesta(2,"snack2", "entrada2", "plato2", "postre2", "adicional2", 100.2, 1, 2))
        propuestasDestacadas.add(Propuesta(3,"snack3", "entrada3", "plato3", "postre3", "adicional3", 100.3, 1, 3))
        propuestasDestacadas.add(Propuesta(4,"snack4", "entrada4", "plato4", "postre4", "adicional4", 100.4, 1, 4))

        propuestasDestacadas.add(Propuesta(5,"snack5", "entrada5", "plato5", "postre5", "adicional5", 100.5, 1, 5))
        propuestasDestacadas.add(Propuesta(6,"snack6", "entrada6", "plato6", "postre6", "adicional6", 100.6, 1, 6))
        propuestasDestacadas.add(Propuesta(7,"snack7", "entrada7", "plato7", "postre7", "adicional7", 100.7, 1, 7))
        propuestasDestacadas.add(Propuesta(8,"snack8", "entrada8", "plato8", "postre8", "adicional8", 100.8, 1, 8))

        propuestasDestacadas.add(Propuesta(9,"snack9", "entrada9", "plato9", "postre9", "adicional9", 100.9, 1, 9))
        propuestasDestacadas.add(Propuesta(10,"snack10", "entrada10", "plato10", "postre10", "adicional10", 100.10, 1, 10))
        propuestasDestacadas.add(Propuesta(11,"snack11", "entrada11", "plato11", "postre11", "adicional11", 100.11, 1, 11))
        propuestasDestacadas.add(Propuesta(12,"snack12", "entrada12", "plato12", "postre12", "adicional12", 100.12, 1, 12))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_cliente, container, false)

        textViewSaludoCliente = v.findViewById(R.id.textViewSaludoCliente)
        textViewProximasReservas = v.findViewById(R.id.textViewProximasReservas)
        textViewReservasAConfirmar = v.findViewById(R.id.textViewReservasAConfirmar)
        textViewReservasPendientes = v.findViewById(R.id.textViewReservasPendientes)
        textViewPropuestasDestacadas = v.findViewById(R.id.textViewPropuestasDestacadas)

        buttonIniciarReserva = v.findViewById(R.id.buttonIniciarReserva)
        buttonVerMisReservas = v.findViewById(R.id.buttonVerMisReservas)

        rvProximaReserva = v.findViewById(R.id.rvProximaReserva)
        rvReservasAConfirmar = v.findViewById(R.id.rvReservasAConfirmar)
        rvReservasPendientes = v.findViewById(R.id.rvReservasPendientes)
        rvPropuestasDestacadas = v.findViewById(R.id.rvPropuestasDestacadas)

        return v
    }

    override fun onStart() {
        super.onStart()

        rvProximaReserva.setHasFixedSize(true)
        rvProximaReserva.layoutManager = LinearLayoutManager(context)
        rvProximaReserva.adapter = VistaReservasAdapter(reservasProximas, requireContext()){
                position -> onItemReservaProximaClick(position)
        }

        rvReservasAConfirmar.setHasFixedSize(true)
        rvReservasAConfirmar.layoutManager = LinearLayoutManager(context)
        rvReservasAConfirmar.adapter = VistaReservasAdapter(reservasAConfirmar, requireContext()){
                position -> onItemReservaAConfirmarClick(position)
        }

        rvReservasPendientes.setHasFixedSize(true)
        rvReservasPendientes.layoutManager = LinearLayoutManager(context)
        rvReservasPendientes.adapter = VistaReservasAdapter(reservasPendientes, requireContext()){
                position -> onItemReservaPendienteClick(position)
        }

        rvPropuestasDestacadas.setHasFixedSize(true)
        rvPropuestasDestacadas.layoutManager = LinearLayoutManager(context)
        rvPropuestasDestacadas.adapter = VistaPropuestasAdapter(propuestasDestacadas, requireContext()){
                position -> onItemPropuestaDestacadaClick(position)
        }

        buttonIniciarReserva.setOnClickListener {
            val iniciarReservaPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToFormularioReservaFragment()
            v.findNavController().navigate(iniciarReservaPage)
        }

        buttonVerMisReservas.setOnClickListener {
            val verMisReservasPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToVistaReservasFragment()
            v.findNavController().navigate(verMisReservasPage)
        }
    }

    private fun onItemReservaProximaClick(position : Int){
        val proxima = reservasProximas[position]
        Snackbar.make(v, "ID de la reserva proxima (son las reservas ya pagadas): " + proxima.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemReservaAConfirmarClick(position : Int){
        val aConfirmar = reservasAConfirmar[position]
        Snackbar.make(v, "ID de la reserva a confirmar (debe ir a la pantalla 'Confirma la reserva)': " + aConfirmar.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemReservaPendienteClick(position : Int){
        val pendiente = reservasPendientes[position]
        Snackbar.make(v, "ID de la reserva pendiente (debe ir a la pantalla 'Confirma la reserva): " + pendiente.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemPropuestaDestacadaClick(position : Int){
        val destacada = propuestasDestacadas[position]
        Snackbar.make(v, "ID de la reserva destacada: " + destacada.id, Snackbar.LENGTH_SHORT).show()
    }

}