package ar.edu.ort.instituto.echeff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfiguracionUsuarioFragment : Fragment() {
    val db = Firebase.firestore
    lateinit var v: View
    lateinit var textViewConfiguracion: TextView
    lateinit var textViewNombreUsuario: TextView
    lateinit var textViewNumberLikes: TextView
    lateinit var imageViewUsuario: ImageView
    lateinit var imageViewLikes: ImageView
    lateinit var switchNotificaciones: Switch
    lateinit var switchNewsletter: Switch
    lateinit var switchPromociones: Switch
    lateinit var switchEmail: Switch
    lateinit var buttonModificarContrasenia: Button
    lateinit var buttonModificarCBU: Button
    lateinit var buttonCerrarSesion: Button
    lateinit var buttonBorrarCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_configuracion_usuario, container, false)

        textViewConfiguracion = v.findViewById(R.id.textViewConfiguracion)
        textViewNombreUsuario = v.findViewById(R.id.textViewNombreUsuario)
        textViewNumberLikes = v.findViewById(R.id.textViewNumberLikes)
        imageViewUsuario = v.findViewById(R.id.imageViewUsuario)
        imageViewLikes = v.findViewById(R.id.imageViewLikes)

        switchNotificaciones = v.findViewById(R.id.switchNotificaciones)
        switchNewsletter = v.findViewById(R.id.switchNewsletter)
        switchPromociones = v.findViewById(R.id.switchPromociones)
        switchEmail = v.findViewById(R.id.switchEmail)

        buttonModificarContrasenia = v.findViewById(R.id.buttonModificarContrasenia)
        buttonModificarCBU = v.findViewById(R.id.buttonModificarCBU)
        buttonCerrarSesion = v.findViewById(R.id.buttonCerrarSesion)
        buttonBorrarCuenta = v.findViewById(R.id.buttonBorrarCuenta)

        return v
    }

    override fun onStart() {
        super.onStart()

        switchNotificaciones.setOnClickListener {
            Snackbar.make(it, "switchNotificaciones.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        switchNewsletter.setOnClickListener {
            Snackbar.make(it, "switchNewsletter.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        switchPromociones.setOnClickListener {
            Snackbar.make(it, "switchPromociones.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        switchEmail.setOnClickListener {
            Snackbar.make(it, "switchEmail.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonModificarContrasenia.setOnClickListener {
            Snackbar.make(it, "buttonModificarContrasenia.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonModificarCBU.setOnClickListener {
            Snackbar.make(it, "buttonModificarCBU.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonCerrarSesion.setOnClickListener {
            Snackbar.make(it, "buttonCerrarSesion.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonBorrarCuenta.setOnClickListener {
            Snackbar.make(it, "buttonBorrarCuenta.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }
    }

}