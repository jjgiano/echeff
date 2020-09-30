package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import ar.edu.ort.instituto.echeff.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.core.view.get

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_registro_usuario, container, false)

        buttonRegistroGoogle = v.findViewById(R.id.buttonRegistroGoogleCliente)
        buttonRegistroFacebook = v.findViewById(R.id.buttonRegistroFacebookCliente)
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

        buttonRegistroGoogle.setOnClickListener{

        }
        buttonRegistroFacebook.setOnClickListener{

        }
        buttonRegistro.setOnClickListener{

        }
    }
}