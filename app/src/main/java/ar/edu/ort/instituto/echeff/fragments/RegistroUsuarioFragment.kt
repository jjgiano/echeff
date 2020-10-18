package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.MainActivity
import ar.edu.ort.instituto.echeff.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates


class RegistroUsuarioFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v: View

    lateinit var buttonRegistroGoogle: Button
    lateinit var buttonRegistroFacebook: Button
    lateinit var checkBoxSoyChef: CheckBox
    lateinit var nombre: EditText
    lateinit var password: EditText
    lateinit var email: EditText
    lateinit var telefono: EditText
    lateinit var buttonRegistro: Button
    lateinit var user: FirebaseUser
    var isNew = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = (activity!!.intent.extras!!.get("user") as FirebaseUser?)!!
        isNew = (activity!!.intent.extras!!.get("isNew") as Boolean?)!!
    }

    fun goToInicio() {
        val action = RegistroUsuarioFragmentDirections.actionRegistroUsuarioFragmentToHomeClienteFragment()
        v.findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_registro_usuario, container, false)
        checkBoxSoyChef = v.findViewById(R.id.checkBoxRegistroClienteSoyChef)
        nombre = v.findViewById(R.id.editTextNombreRegistroCliente)
        password = v.findViewById(R.id.editTextPasswordRegistroCliente)
        email = v.findViewById(R.id.editTextEmailRegistroCliente)
        telefono = v.findViewById(R.id.editTextTelefonoRegistroCliente)
        buttonRegistro = v.findViewById(R.id.buttonRegistroCliente)

        return v
    }

    override fun onStart() {
        super.onStart()
        if (!isNew) {
            goToInicio()
        }

        buttonRegistroGoogle.setOnClickListener {

        }
        buttonRegistroFacebook.setOnClickListener {

        }
        buttonRegistro.setOnClickListener {
            goToInicio()
        }
    }
}