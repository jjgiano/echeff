package ar.edu.ort.instituto.echeff.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Configuracion
import ar.edu.ort.instituto.echeff.fragments.cliente.viewmodel.ViewModelHomeClienteFragment
import ar.edu.ort.instituto.echeff.fragments.viewmodel.ViewModelConfiguracionUsuarioFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ConfiguracionUsuarioFragment : Fragment() {
    lateinit var viewModel: ViewModelConfiguracionUsuarioFragment
    lateinit var sharedPreferences: SharedPreferences
    lateinit var v: View
    lateinit var textViewConfiguracion: TextView
    lateinit var textViewNombreUsuario: TextView
    lateinit var textViewNumberLikes: TextView
    lateinit var imageViewUsuario: ImageView
    lateinit var imageViewLikes: ImageView
    lateinit var switchNotificaciones: androidx.appcompat.widget.SwitchCompat
    lateinit var switchNewsletter: androidx.appcompat.widget.SwitchCompat
    lateinit var switchPromociones: androidx.appcompat.widget.SwitchCompat
    lateinit var switchEmail: androidx.appcompat.widget.SwitchCompat
    lateinit var buttonModificarContrasenia: Button
    lateinit var buttonModificarCBU: Button
    lateinit var buttonCerrarSesion: Button
    lateinit var buttonBorrarCuenta: Button
    lateinit var config: Configuracion

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
        this.setSharedPreferences()
        var userId = this.sharedPreferences.getString("userId","0")!!
        viewModel = ViewModelProvider(super.requireActivity()).get(ViewModelConfiguracionUsuarioFragment::class.java)
        viewModel.getConfiguracion(userId)
        viewModel.liveDataConfig.observe(viewLifecycleOwner, Observer { cnf ->
            this.config = cnf
            switchNotificaciones.isChecked = cnf.notificaciones
            switchNewsletter.isChecked = cnf.newsletter
            switchPromociones.isChecked = cnf.promociones
            switchEmail.isChecked = cnf.emails
        })

        var nombre : String = sharedPreferences.getString("userDisplayName","Nombre No encontrado")!!
        textViewNombreUsuario.text = nombre

        // TODO: levantar la foto del chef del firebase
        Glide
            .with(super.requireContext())
            .load("https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/2_avatar-256.png")
            .centerInside()
            .into(imageViewUsuario)

        switchNotificaciones.setOnClickListener {
            this.config.notificaciones = !this.config.notificaciones
            viewModel.changeConfiguracion(this.config)
            Snackbar.make(it, "Valor Notificaciones: " + this.config.notificaciones, Snackbar.LENGTH_LONG).show()
        }

        switchNewsletter.setOnClickListener {
            this.config.newsletter = !this.config.newsletter
            viewModel.changeConfiguracion(this.config)
            Snackbar.make(it, "switchNewsletter.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        switchPromociones.setOnClickListener {
            this.config.promociones = !this.config.promociones
            viewModel.changeConfiguracion(this.config)
            Snackbar.make(it, "switchPromociones.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        switchEmail.setOnClickListener {
            this.config.emails = !this.config.emails
            viewModel.changeConfiguracion(this.config)
            Snackbar.make(it, "switchEmail.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        buttonModificarContrasenia.setOnClickListener {
            Snackbar.make(it, "buttonModificarContrasenia.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        buttonModificarCBU.setOnClickListener {
            Snackbar.make(it, "buttonModificarCBU.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        buttonCerrarSesion.setOnClickListener {
            Snackbar.make(it, "buttonCerrarSesion.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }

        buttonBorrarCuenta.setOnClickListener {
            Snackbar.make(it, "buttonBorrarCuenta.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }

}

