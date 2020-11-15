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

    private lateinit var idChef: String
    private var radioButtons = ArrayList<RadioButton>()
    private var puntacionIndicada: Int = 0
    private lateinit var puntuacion: Puntuacion

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
        idReserva = CalificarServicioFragmentArgs.fromBundle(requireArguments()).idReserva

        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelCalificarServicioFragment::class.java)

        viewModel.propuesta.observe(viewLifecycleOwner, Observer { propuesta ->
            idChef = propuesta.idChef
        })

        viewModel.puntuacion.observe(viewLifecycleOwner, Observer { puntuacion ->
            this.puntuacion = puntuacion
            if (puntuacion.idChef.isNotEmpty()) {
                setValueRadioButton(puntuacion.idPuntuacion)
                disableRadioButtons()
                btnEnviarCalificacion.isEnabled = false
                btnEnviarCalificacion.text = "YA CALIFICADO"
                tbxComentario.setText(puntuacion.mensaje)
                tbxComentario.isEnabled = false
            }else{
                btnEnviarCalificacion.text = "ENVIAR CALIFICACION"
                tbxComentario.setText("")
                cleanCheckBoxes()
                btnEnviarCalificacion.isEnabled = false
                createRadioListener()
                enableRadioButtons()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPuntuacionActual(idReserva)
        viewModel.getPropuestaByReserva(idReserva)

        radioButtons = ArrayList()
        radioButtons.add(rdbOpcionUno)
        radioButtons.add(rdbOpcionDos)
        radioButtons.add(rdbOpcionTres)
        radioButtons.add(rdbOpcionCuatro)

        btnEnviarCalificacion.isEnabled = false

        btnEnviarCalificacion.setOnClickListener {
            val puntuacionCliente = Puntuacion(
                puntacionIndicada,
                tbxComentario.text.toString(),
                idChef,
                idReserva,
                ""
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

    private fun setValueRadioButton(id: Int) {
        radioButtons[id - 1].isChecked = true
    }

    private fun disableRadioButtons() {
        radioButtons.forEach { radio ->
            radio.isEnabled = false
        }
    }
    private fun enableRadioButtons() {
        radioButtons.forEach { radio ->
            radio.isEnabled = true
        }
    }
}