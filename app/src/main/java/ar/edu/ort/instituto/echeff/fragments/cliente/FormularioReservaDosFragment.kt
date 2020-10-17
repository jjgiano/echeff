package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_formulario_reserva_dos.*


class FormularioReservaDosFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v : View

    //lateinit var stringFecha : String
    //lateinit var stringHora : String
    //var cantComensales : Int = 0
    //lateinit var stringTipoCocina : String
    //lateinit var stringDireccion : String
    //var boolTieneHorno : Boolean = false

    lateinit var spinnerEstiloComida : Spinner
    lateinit var etAclaracionesAChef : EditText
    lateinit var spinnerTipoServicio : Spinner
    lateinit var btnCrearReserva : Button
    lateinit var textViewImporte : TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_formulario_reserva_dos, container, false)

        spinnerEstiloComida = v.findViewById(R.id.spinner_formularioReserva2_estiloComida)
        val adapter = ArrayAdapter.createFromResource(v.context,R.array.spinner_values_estilosComida,R.layout.spinner_item)
        spinnerEstiloComida.adapter = adapter

        spinnerTipoServicio = v.findViewById(R.id.spinner_formularioReserva2_tipoServicio)
        val adapter2 = ArrayAdapter.createFromResource(v.context,R.array.spinner_values_tiposServicio,R.layout.spinner_item)
        spinnerTipoServicio.adapter = adapter2

        //var posTipoServicio = spinnerTipoServicio.selectedItemPosition
        ////var stringTipoServicio = spinnerTipoServicio.selectedItem.toString()


        btnCrearReserva = v.findViewById(R.id.btn_formularioReserva2_crearReserva)
        etAclaracionesAChef = v.findViewById(R.id.editText_formularioReserva2_aclaracionesAChef)
        textViewImporte = v.findViewById(R.id.textView_formularioReserva2_importe)




        return v
    }

    override fun onStart() {
        super.onStart()

        btnCrearReserva.setOnClickListener {
            var homeClienteScreen = FormularioReservaDosFragmentDirections.actionFormularioReservaDosFragment3ToHomeClienteFragment()
            v.findNavController().navigate(homeClienteScreen)
        }
    }


}