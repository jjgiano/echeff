package ar.edu.ort.instituto.echeff.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Problema
import ar.edu.ort.instituto.echeff.entities.TipoResultadoMensaje
import ar.edu.ort.instituto.echeff.fragments.viewmodel.ViewModelMesaAyudaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities

class MesaAyudaFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModel: ViewModelMesaAyudaFragment
    lateinit var v: View
    lateinit var spinnerSugerenciasMesaAyuda: Spinner
    lateinit var editTextMesaAyuda: EditText
    lateinit var buttonMesaAyuda: Button
    var problema: Problema = Problema()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelMesaAyudaFragment::class.java)
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
        this.setSharedPreferences()
        var userId = this.sharedPreferences.getString("userId","0")!!

        spinnerSugerenciasMesaAyuda.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                problema.opcion = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO(Not Implemented yet)
            }

        }

        buttonMesaAyuda.setOnClickListener {
            problema.comentarios = editTextMesaAyuda.text.toString()
            problema.idUsuario = userId
            viewModel.crearProblema(problema)
            v.findNavController().navigate(
                MesaAyudaFragmentDirections.actionMesaAyudaFragment2ToResultadoMensajeFragment(
                    TipoResultadoMensaje.NUEVO_MESA_AYUDA,
                    MesaAyudaFragmentArgs.fromBundle(requireArguments()).soyChef
                )
            )
        }
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }

}