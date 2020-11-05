package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    val db = Firebase.firestore
    lateinit var v: View
    lateinit var textViewTitulo: TextView
    lateinit var editTextTextUsuario: EditText
    lateinit var editTextTextPassword: EditText
    lateinit var buttonIngresar: Button
    lateinit var buttonRegistrate: Button
    lateinit var buttonGoogle: Button
    lateinit var buttonFacebook: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false)

        textViewTitulo = v.findViewById(R.id.textViewTitulo)
        editTextTextUsuario = v.findViewById(R.id.emailEditText)
        editTextTextPassword = v.findViewById(R.id.passwordEditText)
        buttonIngresar = v.findViewById(R.id.buttonIngresar)
        buttonRegistrate = v.findViewById(R.id.buttonRegistrate)
        buttonGoogle = v.findViewById(R.id.buttonGoogle)
        buttonFacebook = v.findViewById(R.id.buttonFacebook)



        return v
    }

    override fun onStart() {
        super.onStart()
      /*  buttonIngresar.setOnClickListener {
            Snackbar.make(it, "buttonIngresar.setOnClickListener", Snackbar.LENGTH_SHORT).show()
            val city = hashMapOf(
                "platoPrincipal" to "Los Angeles",
                "state" to "CA",
                "country" to "USA"
            )


            db.collection("reservas").add(city)
        }*/

        buttonRegistrate.setOnClickListener {
            Snackbar.make(it, "buttonRegistrate.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonGoogle.setOnClickListener {
            Snackbar.make(it, "buttonGoogle.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonFacebook.setOnClickListener {
            Snackbar.make(it, "buttonGoogle.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }
    }

}