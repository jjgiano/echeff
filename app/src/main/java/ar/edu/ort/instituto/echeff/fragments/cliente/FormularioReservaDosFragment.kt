package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelReservaDosFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FormularioReservaDosFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v: View

    lateinit var reserva: Reserva

    lateinit var spinnerEstiloComida: Spinner
    lateinit var etAclaracionesAChef: EditText
    lateinit var spinnerTipoServicio: Spinner
    lateinit var btnCrearReserva: Button
    lateinit var textViewImporte: TextView

    lateinit var viewModel: ViewModelReservaDosFragment

    var tipoServiciosList: MutableList<TipoServicio> = ArrayList()
    var tipoServicio = TipoServicio()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_formulario_reserva_dos, container, false)

        spinnerEstiloComida = v.findViewById(R.id.spinner_formularioReserva2_estiloComida)
        val adapter = ArrayAdapter.createFromResource(
            v.context,
            R.array.spinner_values_estilosComida,
            R.layout.spinner_item
        )
        spinnerEstiloComida.adapter = adapter

        spinnerTipoServicio = v.findViewById(R.id.spinner_formularioReserva2_tipoServicio)

        val adapter2 = ArrayAdapter.createFromResource(
            v.context,
            R.array.spinner_values_tiposServicio,
            R.layout.spinner_item
        )
        spinnerTipoServicio.adapter = adapter2

        btnCrearReserva = v.findViewById(R.id.btn_formularioReserva2_crearReserva)
        etAclaracionesAChef = v.findViewById(R.id.editText_formularioReserva2_aclaracionesAChef)
        textViewImporte = v.findViewById(R.id.textView_formularioReserva2_importe)

        reserva = FormularioReservaDosFragmentArgs.fromBundle(requireArguments()).reserva

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelReservaDosFragment::class.java)

        viewModel.tipoServicioLiveData.observe(viewLifecycleOwner, Observer { tipoServicios ->
            spinnerTipoServicio.adapter =
                ArrayAdapter(requireContext(), R.layout.spinner_item, tipoServicios)
            tipoServiciosList = tipoServicios
        })
    }

    override fun onStart() {
        super.onStart()

        viewModel.obtenerTipoServicios()

        spinnerTipoServicio.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (tipoServiciosList.size != 0) {

                    tipoServicio = parent.getItemAtPosition(position) as TipoServicio
                    textViewImporte.text = view.context.getString(
                        R.string.rango_precio,
                        tipoServicio.precioMinimo,
                        tipoServicio.precioMaximo
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO(Not Implemented yet)
            }

        }

        btnCrearReserva.setOnClickListener {
            reserva.estiloCocina = spinnerEstiloComida.selectedItem.toString()
            reserva.tipoServicio = spinnerTipoServicio.selectedItem.toString()
            reserva.notas = etAclaracionesAChef.text.toString()

            db.collection("reservas").add(reserva).addOnSuccessListener { result ->
                reserva.id = result.id
                db.collection("reservas").document(reserva.id).set(reserva)

                var resultadoMensajeScreen =
                    FormularioReservaDosFragmentDirections.actionFormularioReservaDosFragment3ToResultadoMensajeFragment(
                        TipoResultadoMensaje.NUEVA_RESERVA
                    )
                v.findNavController().navigate(resultadoMensajeScreen)
            }
        }
    }


}