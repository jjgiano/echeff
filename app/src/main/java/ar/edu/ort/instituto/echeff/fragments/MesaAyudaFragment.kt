package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mesa_ayuda.*


class MesaAyudaFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v: View

    lateinit var spinner: Spinner
    lateinit var editText: EditText
    lateinit var button: Button

    lateinit var stringItemSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_mesa_ayuda, container, false)

        spinner = v.findViewById(R.id.spinnerSugerenciasMesaAyuda)
        val adapter = ArrayAdapter.createFromResource(v.context,R.array.spinner_values,R.layout.spinner_item)
        spinner.adapter = adapter

        editText = v.findViewById(R.id.editTextMesaAyuda)
        button = v.findViewById(R.id.buttonMesaAyuda)

        return v
    }

    override fun onStart() {
        super.onStart()

        button.setOnClickListener {
            //v.findNavController().navigate(MesaAyudaFragmentDirections.actionMesaAyudaFragmentToHomeChefFragment())
            //v.findNavController().navigate(MesaAyudaFragmentDirections.actionMesaAyudaFragmentToHomeClienteFragment())
            stringItemSelected = spinner.selectedItem.toString()
            Snackbar.make(it, stringItemSelected, Snackbar.LENGTH_SHORT).show()
        }


    }

}