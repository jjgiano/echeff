package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.adapters.VistaReservasAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelHomeClienteFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.google.android.material.snackbar.Snackbar

class HomeClienteFragment : Fragment() {
    lateinit var v: View
    private lateinit var viewModel: ViewModelHomeClienteFragment
    var cargar : Boolean = false

    lateinit var textViewSaludoCliente: TextView
    lateinit var textViewProximasReservas: TextView
    lateinit var textViewReservasAConfirmar: TextView
    lateinit var textViewReservasPendientes: TextView
    lateinit var textViewPropuestasDestacadas: TextView

    lateinit var buttonIniciarReserva: Button
    lateinit var buttonVerMisReservas: Button
    lateinit var buttonConfiguracionPerfilCliente: Button

    lateinit var rvProximaReserva: RecyclerView
    lateinit var rvReservasAConfirmar: RecyclerView
    lateinit var rvReservasPendientes: RecyclerView
    lateinit var rvPropuestasDestacadas: RecyclerView

    lateinit var sharedPreferences: SharedPreferences

    var reservasProximas: MutableList<Reserva> = ArrayList<Reserva>()
    var propuestasAConfirmar: MutableList<Propuesta> = ArrayList<Propuesta>()
    var reservasPendientes: MutableList<Reserva> = ArrayList<Reserva>()
    var propuestas: MutableList<Propuesta> = ArrayList<Propuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(super.requireActivity()).get(ViewModelHomeClienteFragment::class.java)

        viewModel.liveDataBooleanCargar.observe(viewLifecycleOwner, Observer { result ->
            cargar = result
        })

        viewModel.liveDataReservasProximasList.observe(viewLifecycleOwner, Observer { result ->
            reservasProximas = result
            rvProximaReserva.setHasFixedSize(true)
            rvProximaReserva.layoutManager = LinearLayoutManager(context)
            rvProximaReserva.adapter = VistaReservasAdapter(reservasProximas, super.requireContext()){
                    position -> onItemReservaProximaClick(position)
            }
        })

        viewModel.liveDataPropuestasAConfirmarList.observe(viewLifecycleOwner, Observer { result ->
            propuestasAConfirmar = result
            rvReservasAConfirmar.setHasFixedSize(true)
            rvReservasAConfirmar.layoutManager = LinearLayoutManager(context)
            rvReservasAConfirmar.adapter = VistaPropuestasAdapter(propuestasAConfirmar, super.requireContext()){
                    position -> onItemPropuestaAConfirmarClick(position)
            }
        })

        viewModel.liveDataReservasPendientesList.observe(viewLifecycleOwner, Observer { result ->
            reservasPendientes = result
            rvReservasPendientes.setHasFixedSize(true)
            rvReservasPendientes.layoutManager = LinearLayoutManager(context)
            rvReservasPendientes.adapter = VistaReservasAdapter(reservasPendientes, super.requireContext()){
                    position -> onItemReservaPendienteClick(position)
            }
        })

        viewModel.liveDataPropuestasDestacadasList.observe(viewLifecycleOwner, Observer { result ->
            propuestas = result
            rvPropuestasDestacadas.setHasFixedSize(true)
            rvPropuestasDestacadas.layoutManager = LinearLayoutManager(context)
            rvPropuestasDestacadas.adapter = VistaPropuestasAdapter(propuestas, super.requireContext()){
                    position -> onItemPropuestaDestacadaClick(position)
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_cliente, container, false)

        textViewSaludoCliente = v.findViewById(R.id.textViewSaludoCliente)
        textViewProximasReservas = v.findViewById(R.id.textViewProximasReservas)
        textViewReservasAConfirmar = v.findViewById(R.id.textViewReservasAConfirmar)
        textViewReservasPendientes = v.findViewById(R.id.textViewReservasPendientes)
        textViewPropuestasDestacadas = v.findViewById(R.id.textViewPropuestasDestacadas)

        buttonIniciarReserva = v.findViewById(R.id.buttonIniciarReserva)
        buttonVerMisReservas = v.findViewById(R.id.buttonVerMisReservas)
        buttonConfiguracionPerfilCliente = v.findViewById(R.id.buttonConfiguracionPerfilCliente)

        rvProximaReserva = v.findViewById(R.id.rvProximaReserva)
        rvReservasAConfirmar = v.findViewById(R.id.rvReservasAConfirmar)
        rvReservasPendientes = v.findViewById(R.id.rvReservasPendientes)
        rvPropuestasDestacadas = v.findViewById(R.id.rvPropuestasDestacadas)

        return v
    }

    override fun onStart() {
        super.onStart()
        setSharedPreferences()
        textViewSaludoCliente.text = "Hola, " + sharedPreferences.getString("userDisplayName", "")
        var userId = sharedPreferences.getString("userId", "0")!!
        viewModel.setCargar(userId)

        buttonIniciarReserva.setOnClickListener {
            val iniciarReservaPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToFormularioReservaFragment()
            v.findNavController().navigate(iniciarReservaPage)
        }

        buttonVerMisReservas.setOnClickListener {
            val verMisReservasPage = HomeClienteFragmentDirections.actionHomeClienteFragmentToVistaReservasFragment()
            v.findNavController().navigate(verMisReservasPage)
        }

        buttonConfiguracionPerfilCliente.setOnClickListener {
            val misConfiguraciones = HomeClienteFragmentDirections.actionHomeClienteFragmentToConfiguracionUsuarioFragment2()
            v.findNavController().navigate(misConfiguraciones)
        }

    }

    private fun onItemReservaProximaClick(position: Int) {
        val proxima = reservasProximas[position]
        Snackbar.make(v, "LA RESERVA YA SE PAGO: " + proxima.id, Snackbar.LENGTH_LONG).show()
    }

    private fun onItemReservaPendienteClick(position: Int) {
        val pendiente = reservasPendientes[position]
        Snackbar.make(v,"LA RESERVA LO TIENE EL CHEF AHORA: " + pendiente.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemPropuestaAConfirmarClick(position: Int) {
        val aConfirmar: Propuesta = propuestasAConfirmar[position]
        sharedPreferences.edit().putString("idPropuesta", aConfirmar.id).apply()
        var confirmacionReservaScreen = HomeClienteFragmentDirections.actionHomeClienteFragmentToConfirmacionReservaFragment2()
        v.findNavController().navigate(confirmacionReservaScreen)
    }

    private fun onItemPropuestaDestacadaClick(position: Int) {
        val destacada = propuestas[position]
        Snackbar.make(v, "ID de la reserva destacada: " + destacada.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }
}