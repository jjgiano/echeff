package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.*
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelFormularioPropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_inicio.view.*

class FormularioPropuestaFragment : Fragment() {
    lateinit var v: View
    private lateinit var viewModelPropuesta: ViewModelFormularioPropuestaFragment
    private lateinit var viewModelReserva: ViewModelReservasConfirmarFragment
    private lateinit var viewModelDetalleReserva: ViewModelDetalleReservaFragment


    var nuevaPropuesta: Propuesta = Propuesta()

    // datos de la reserva
    lateinit var usuario: TextView
    lateinit var comensales: TextView
    lateinit var estilococina: TextView
    lateinit var reserva: Reserva
    lateinit var servicio: TextView
    lateinit var imagenCliente : ImageView

    var modificado: Boolean = false
    var cliente: Cliente = Cliente()

    //los input de la propuesta
    lateinit var editText_Snack: EditText
    lateinit var editText_Entrada: EditText
    lateinit var editText_PlatoPricipal: EditText
    lateinit var editText_Postre: EditText
    lateinit var editText_Adicional: EditText
    lateinit var editText_Importe: EditText

    //botones de Guardar y Enviar
    lateinit var btn_Propuesta: Button
    lateinit var btn_EditarPropuesta: FloatingActionButton
    lateinit var btn_EnviarPropuesta: Button

    var importeLleno = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_propuesta, container, false)
        //datos de la Tarjeta de reserva
        imagenCliente = v.findViewById(R.id.imageViewChef)
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        servicio = v.findViewById(R.id.text_DatoTipoServicio)


        //Formulario Propuesta
        editText_Snack = v.findViewById(R.id.editText_Snack)
        editText_Entrada = v.findViewById(R.id.editText_Entrada)
        editText_PlatoPricipal = v.findViewById(R.id.editText_PlatoPrincipal)
        editText_Postre = v.findViewById(R.id.editText_Postre)
        editText_Adicional = v.findViewById(R.id.editText_Adicionales)
        editText_Importe = v.findViewById(R.id.editText_Total)

        //Boton de Propuesta
        btn_Propuesta = v.findViewById(R.id.btn_GuardarPropuesta)
        btn_EditarPropuesta = v.findViewById(R.id.btn_EditarPropuesta)
        btn_EnviarPropuesta = v.findViewById(R.id.btn_EnviarPropuesta)

        btn_EnviarPropuesta.visibility = View.INVISIBLE;
        btn_EditarPropuesta.visibility = View.INVISIBLE;


        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelPropuesta =
            ViewModelProvider(requireActivity()).get(ViewModelFormularioPropuestaFragment::class.java)
        viewModelReserva =
            ViewModelProvider(requireActivity()).get(ViewModelReservasConfirmarFragment::class.java)
        viewModelDetalleReserva =
            ViewModelProvider(requireActivity()).get(ViewModelDetalleReservaFragment::class.java)

        viewModelDetalleReserva.cliente.observe(viewLifecycleOwner, Observer { result ->

            cliente = result


            llenarFichaReserva()
        })
    }

    override fun onStart() {
        super.onStart()

        var sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor, Context.MODE_PRIVATE)
        var idUsuario: String = sharedPref.getString("userId", "Vacio")!!

        changStatebutton(btn_Propuesta ,importeLleno)
        //Obtengo la reserva que llega por Argumentos de navegacion
        reserva =
            FormularioPropuestaFragmentArgs.fromBundle(requireArguments()).formularioPropuestaArg
        viewModelReserva.setcargar(reserva.idUsuario)

        validate();

        //Boton de Guardar la Propuesta y cambiar los botones y blockear los EditText
        btn_Propuesta.setOnClickListener {

            //guardo la propuesta


            //Si se modifico guardo los dato sen la nueva Propuesta
            nuevaPropuesta.snack = editText_Snack.text.toString()
            nuevaPropuesta.entrada = editText_Entrada.text.toString()
            nuevaPropuesta.plato = editText_PlatoPricipal.text.toString()
            nuevaPropuesta.postre = editText_Postre.text.toString()
            nuevaPropuesta.adicional = editText_Adicional.text.toString()
            nuevaPropuesta.total = editText_Importe.text.toString().toDouble()
            nuevaPropuesta.idReserva = reserva.id
            nuevaPropuesta.idChef = idUsuario
            nuevaPropuesta.idEstadoPropuesta = EstadoPropuesta.NUEVO.id
            nuevaPropuesta.importeTotal = editText_Importe.text.toString().toDouble() * reserva.comensales

            //Guardo o modifico en Firebase
            if (modificado) {
                viewModelPropuesta.modificarPropuesta(nuevaPropuesta)
            } else {
                viewModelPropuesta.guardarPropuesta(nuevaPropuesta)
            }

            //Cambio los botones y blockeo los textos
            btn_EditarPropuesta.visibility = View.VISIBLE;
            btn_EnviarPropuesta.visibility = View.VISIBLE;
            btn_Propuesta.visibility = View.INVISIBLE;
            editText_Snack.isFocusable = false
            editText_Entrada.isFocusable = false
            editText_PlatoPricipal.isFocusable = false
            editText_Postre.isFocusable = false
            editText_Adicional.isFocusable = false
            editText_Importe.isFocusable = false
        }

        // Boton de envio de Propuesta
        btn_EnviarPropuesta.setOnClickListener {
            nuevaPropuesta.idEstadoPropuesta = EstadoPropuesta.ACONFIRMAR.id
            viewModelPropuesta.modificarPropuesta(nuevaPropuesta)
            viewModelReserva.pasarAConfirmar(reserva)
            val action =
                FormularioPropuestaFragmentDirections.actionFormularioPropuestaFragmentToResultadoMensajeFragment(
                    TipoResultadoMensaje.NUEVA_PROPUESTA,
                    true
                )
            v.findNavController().navigate(action)
        }

        btn_EditarPropuesta.setOnClickListener {
            modificado = true
            //modificar la vista de los botones

            btn_EnviarPropuesta.visibility = View.INVISIBLE;
            btn_EditarPropuesta.visibility = View.INVISIBLE;
            btn_Propuesta.visibility = View.VISIBLE;

            editText_Snack.isFocusable = true
            editText_Snack.isClickable = true
            editText_Snack.isEnabled = true
            editText_Entrada.isFocusable = true
            editText_Entrada.isClickable = true
            editText_PlatoPricipal.isFocusable = true
            editText_PlatoPricipal.isClickable = true
            editText_Postre.isFocusable = true
            editText_Postre.isClickable = true
            editText_Adicional.isFocusable = true
            editText_Adicional.isClickable = true
            editText_Importe.isFocusable = true
            editText_Importe.isClickable = true
            editText_Importe.isEnabled = true
        }
    }

    fun validate() {
        editText_Importe.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newInput: Editable?) {
                val content = newInput.toString()
                var enable = false
               importeLleno = content.isNotEmpty()
                changStatebutton(btn_Propuesta, importeLleno)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

    }

    fun changStatebutton(button: Button, enable: Boolean) {
        button.isEnabled = enable
    }

    fun llenarFichaReserva() {
        //lleno los datos de la reserva
        val storage = FirebaseStorage.getInstance()
        var url = String()
        var ref: StorageReference
        url = cliente.urlFoto

        if (!url.isNotEmpty()) url = "gs://pf2020-echeff.appspot.com/SinFoto.jpg"
        //busco la referencia por el URL
        if (url.startsWith("gs://", 0, true)) {
            ref = storage.getReferenceFromUrl(url)
        } else {
            ref = storage.getReference(url)
        }


        GlideApp.with(this)
            .load(ref)
            .into(imagenCliente)

        usuario.text = cliente.nombre
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio
    }
}