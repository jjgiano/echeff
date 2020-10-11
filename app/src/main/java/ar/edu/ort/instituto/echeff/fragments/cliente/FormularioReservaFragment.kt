package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FormularioReservaFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v : View

    lateinit var etFecha : EditText
    lateinit var etHora : EditText
    lateinit var etCantComensales : EditText
    lateinit var etDireccion : EditText
    lateinit var spinnerTipoCocina : Spinner
    lateinit var cbTengoHorno : CheckBox
    lateinit var btnContinuar : Button


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_reserva, container, false)

        etFecha = v.findViewById(R.id.editText_formularioReserva_fecha)
        etHora = v.findViewById(R.id.editText_formularioReserva_hora)
        etCantComensales = v.findViewById(R.id.editText_formularioReserva_cantidadComensales)
        etDireccion = v.findViewById(R.id.editText_formularioReserva_direccion)
        cbTengoHorno = v.findViewById(R.id.checkBox_formularioReserva_tengoHorno)
        btnContinuar = v.findViewById(R.id.btn_formularioReserva_continuar)

        spinnerTipoCocina = v.findViewById(R.id.spinner_formularioReserva_tipoCocina)
        val adapter = ArrayAdapter.createFromResource(v.context,R.array.spinner_values_tiposCocina,R.layout.spinner_item)
        spinnerTipoCocina.adapter = adapter

        return v
    }

    override fun onStart() {
        super.onStart()

        btnContinuar.setOnClickListener {
            var stringFecha = etFecha.text.toString()
            var stringHora = etHora.text.toString()
            var cantComensales = etCantComensales.text.toString().toInt()
            var stringTipoCocina = spinnerTipoCocina.selectedItem.toString()
            var stringDireccion = etDireccion.text.toString()
            var boolTieneHorno = cbTengoHorno.isChecked.toString()

            var reserva = Reserva(1, stringFecha,stringHora,stringDireccion,stringTipoCocina,boolTieneHorno.toString(), cantComensales, "","","",1)
            //todo hacer este insert en la proxima pagina, enviar el objeto
            db.collection("reservas").add(reserva)
                //Con estop podes retornar le ultimo id
               //.addOnSuccessListener { result ->  Log.d(TAG, "DocumentSnapshot written with ID: ${result.id}") }
               //.addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


            val action2 = FormularioReservaFragmentDirections.actionFormularioReservaFragmentToFormularioReservaDosFragment3()
            v.findNavController().navigate(action2)
        }
    }
}