package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.TipoResultadoMensaje
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MesaAyudaFragment : Fragment() {

    val db = Firebase.firestore
    var soyChef: Boolean = true
    lateinit var v: View
    lateinit var spinnerSugerenciasMesaAyuda: Spinner
    lateinit var editTextMesaAyuda: EditText
    lateinit var buttonMesaAyuda: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_mesa_ayuda, container, false)
        spinnerSugerenciasMesaAyuda = v.findViewById(R.id.spinnerSugerenciasMesaAyuda)
        val adapter = ArrayAdapter.createFromResource(
            v.context,
            R.array.spinner_values_mesaDeAyudaSugerencias,
            R.layout.spinner_item
        )
        spinnerSugerenciasMesaAyuda.adapter = adapter
        editTextMesaAyuda = v.findViewById(R.id.editTextMesaAyuda)
        buttonMesaAyuda = v.findViewById(R.id.buttonMesaAyuda)
        return v
    }

    override fun onStart() {
        super.onStart()
        buttonMesaAyuda.setOnClickListener {
            v.findNavController().navigate(
                MesaAyudaFragmentDirections.actionMesaAyudaFragment2ToResultadoMensajeFragment(
                    TipoResultadoMensaje.NUEVO_MESA_AYUDA,
                    MesaAyudaFragmentArgs.fromBundle(requireArguments()).soyChef
                )
            )
        }
    }
}