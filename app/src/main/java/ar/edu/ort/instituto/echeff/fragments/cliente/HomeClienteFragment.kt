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
        var confirmacionReservaScreen = HomeClienteFragmentDirections.actionHomeClienteFragmentToConfirmacionReservaFragment2()
        v.findNavController().navigate(confirmacionReservaScreen)
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