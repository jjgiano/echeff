package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import ar.edu.ort.instituto.echeff.entities.TipoResultadoMensaje
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelCalificarServicioFragment


class CalificarServicioFragment : Fragment() {
    lateinit var v: View
    private lateinit var btnOpcionUno: ImageView
    private lateinit var btnOpcionDos: ImageView
    private lateinit var btnOpcionTres: ImageView
    private lateinit var btnOpcionCuatro: ImageView
    private lateinit var tbxComentario: EditText
    private lateinit var btnEnviarCalificacion: Button
    private lateinit var rdbOpcionUno: RadioButton
    private lateinit var rdbOpcionDos: RadioButton
    private lateinit var rdbOpcionTres: RadioButton
    private lateinit var rdbOpcionCuatro: RadioButton
    private lateinit var viewModel: ViewModelCalificarServicioFragment
    private lateinit var idReserva: String

    private var idChef: String = ""
    private var radioButtons = ArrayList<RadioButton>()
    private var puntacionIndicada: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calificar_servicio, container, false)

        btnOpcionUno = v.findViewById(R.id.btnOpcionUno)
        btnOpcionDos = v.findViewById(R.id.btnOpcionDos)
        btnOpcionTres = v.findViewById(R.id.btnOpcionTres)
        btnOpcionCuatro = v.findViewById(R.id.btnOpcionCuatro)
        tbxComentario = v.findViewById(R.id.tbxComentario)
        btnEnviarCalificacion = v.findViewById(R.id.btnEnviarCalificacion)
        rdbOpcionUno = v.findViewById(R.id.rdbOpcionUno)
        rdbOpcionDos = v.findViewById(R.id.rdbOpcionDos)
        rdbOpcionTres = v.findViewById(R.id.rdbOpcionTres)
        rdbOpcionCuatro = v.findViewById(R.id.rdbOpcionCuatro)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        idReserva  = CalificarServicioFragmentArgs.fromBundle(requireArguments()).idReserva
        idChef = ""

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelCalificarServicioFragment::class.java)

        viewModel.getPropuestaByReserva(idReserva)
        viewModel.liveDataPropuesta.observe(viewLifecycleOwner, Observer { propuesta ->
            //Log.e("TAG", propuesta.id);
            idChef = propuesta.idChef
        })
    }

    override fun onStart() {
        super.onStart()

        radioButtons = ArrayList()

        radioButtons.add(rdbOpcionUno)
        radioButtons.add(rdbOpcionDos)
        radioButtons.add(rdbOpcionTres)
        radioButtons.add(rdbOpcionCuatro)

        btnEnviarCalificacion.isEnabled = false

        createRadioListener()

        btnEnviarCalificacion.setOnClickListener {
            val puntuacionCliente = Puntuacion(
                puntacionIndicada,
                tbxComentario.text.toString(),
                idChef
            )

            viewModel.cargarNuevaPuntuacion(puntuacionCliente)

            v.findNavController().navigate(
                CalificarServicioFragmentDirections.actionCalificarServicioFragmentToResultadoMensajeFragment(
                    TipoResultadoMensaje.COMENTARIO_ENVIADO
                )
            )
        }
    }

    private fun cleanCheckBoxes() {
        for (rb: RadioButton in radioButtons) {
            if (rb.isChecked) {
                rb.isChecked = false
            }
        }
    }

    private fun createRadioListener() {
        radioButtons.forEachIndexed { index, radio ->
            radio.setOnCheckedChangeListener { buttonView, isChecked ->
                cleanCheckBoxes()
                if (isChecked && idChef.isNotEmpty()) {
                    buttonView.isChecked = true
                    btnEnviarCalificacion.isEnabled = true
                    puntacionIndicada = index + 1
                }
            }
        }
    }
}