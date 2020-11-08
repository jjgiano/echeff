package ar.edu.ort.instituto.echeff.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ar.edu.ort.instituto.echeff.InicioActivity
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Configuracion
import ar.edu.ort.instituto.echeff.fragments.viewmodel.ViewModelConfiguracionUsuarioFragment
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar

class ConfiguracionUsuarioFragment : Fragment(), StorageReferenceUtiles {
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
    lateinit var buttonGuardarCBU: Button
    lateinit var buttonCerrarSesion: Button
    lateinit var buttonBorrarCuenta: Button
    lateinit var config: Configuracion
    lateinit var textViewCBU: TextView
    var chef: Chef = Chef()
    var cliente: Cliente = Cliente()

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
        textViewCBU = v.findViewById(R.id.tv_CBU)
        imageViewUsuario = v.findViewById(R.id.imageViewUsuario)
        imageViewLikes = v.findViewById(R.id.imageViewLikes)

        switchNotificaciones = v.findViewById(R.id.switchNotificaciones)
        switchNewsletter = v.findViewById(R.id.switchNewsletter)
        switchPromociones = v.findViewById(R.id.switchPromociones)
        switchEmail = v.findViewById(R.id.switchEmail)

        buttonModificarContrasenia = v.findViewById(R.id.buttonModificarContrasenia)
        buttonModificarCBU = v.findViewById(R.id.buttonModificarCBU)
        buttonGuardarCBU = v.findViewById(R.id.buttonGuardarCBU)
        buttonCerrarSesion = v.findViewById(R.id.buttonCerrarSesion)
        buttonBorrarCuenta = v.findViewById(R.id.buttonBorrarCuenta)

        buttonModificarCBU.visibility = View.INVISIBLE
        textViewCBU.visibility = View.INVISIBLE
        buttonGuardarCBU.visibility = View.INVISIBLE

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(super.requireActivity()).get(ViewModelConfiguracionUsuarioFragment::class.java)

        viewModel.liveDataConfig.observe(viewLifecycleOwner, Observer { cnf ->
            this.config = cnf
            switchNotificaciones.isChecked = cnf.notificaciones
            switchNewsletter.isChecked = cnf.newsletter
            switchPromociones.isChecked = cnf.promociones
            switchEmail.isChecked = cnf.emails
            textViewCBU.text = cnf.CBU

        })
        viewModel.chef.observe(viewLifecycleOwner, Observer { result ->
            chef = result
            if (chef.id.isNotEmpty()) llenarDatos()
        })
        viewModel.cliente.observe(viewLifecycleOwner, Observer { result ->
            cliente = result
            if (cliente.id.isNotEmpty()) llenarDatos()
        })

    }

    override fun onStart() {
        super.onStart()
        this.setSharedPreferences()
        var userId = this.sharedPreferences.getString("userId", "0")!!

        val ischef = sharedPreferences.getBoolean("isChef", false)
        var nombre: String =
            sharedPreferences.getString("userDisplayName", "Nombre No encontrado")!!
        textViewNombreUsuario.text = nombre

        viewModel.getConfiguracion(userId)

        if (ischef) {
            buttonModificarCBU.visibility = View.VISIBLE
            textViewCBU.visibility = View.VISIBLE
        }


        switchNotificaciones.setOnClickListener {
            this.config.notificaciones = !this.config.notificaciones
            viewModel.changeConfiguracion(this.config)
            Snackbar.make(
                it,
                "Valor Notificaciones: " + this.config.notificaciones,
                Snackbar.LENGTH_LONG
            ).show()
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
            Snackbar.make(it, "buttonModificarContrasenia.setOnClickListener", Snackbar.LENGTH_LONG)
                .show()
        }

        buttonModificarCBU.setOnClickListener {
            textViewCBU.isFocusable = true
            textViewCBU.isEnabled = true
            textViewCBU.isClickable = true
            buttonModificarCBU.visibility = View.INVISIBLE
            buttonGuardarCBU.visibility = View.VISIBLE
        }

        buttonGuardarCBU.setOnClickListener {
            config.CBU = textViewCBU.text.toString()
            viewModel.changeConfiguracion(config)
            buttonModificarCBU.visibility = View.VISIBLE
            buttonGuardarCBU.visibility = View.INVISIBLE
        }

        buttonCerrarSesion.setOnClickListener {

            AuthUI.getInstance()
                .signOut(context!!)
                .addOnCompleteListener {
                    sharedPreferences.edit().clear().commit();

                    val intent = Intent(context, InicioActivity::class.java)
                    startActivity(intent)

                }

        }

        buttonBorrarCuenta.setOnClickListener {
            Snackbar.make(it, "buttonBorrarCuenta.setOnClickListener", Snackbar.LENGTH_LONG).show()
        }
    }


    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun llenarDatos() {

       val ischef = sharedPreferences.getBoolean("isChef", false)
        var url : String
        url = if (ischef) {
            chef.urlFoto
        } else {
            cliente.urlFoto
        }

        if (!url.isNotEmpty()) url = EcheffUtilities.SIN_FOTO.valor

        // TODO: levantar la foto del chef del firebase
        GlideApp
            .with(this)
            .load(buscarReferencia(url))
            .centerInside()
            .into(imageViewUsuario)
    }
}

