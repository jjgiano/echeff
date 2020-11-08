package ar.edu.ort.instituto.echeff.fragments.chef.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Cliente
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.utils.GlideApp
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.api.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.InputStream


class DetalleReservaFragment : Fragment() {

    lateinit var v :View
    lateinit var nombre: TextView
    lateinit var fecha: TextView
    lateinit var hora: TextView
    lateinit var direccion: TextView
    lateinit var tipoCocina: TextView
    lateinit var tieneHorno: CheckBox
    lateinit var comensales: TextView
    lateinit var estiloCocina: TextView
    lateinit var tipoServicio : TextView
    lateinit var notas: TextView
    lateinit var idUsuario: TextView
    lateinit var reserva : Reserva
    lateinit var imagenCliente: ImageView

    private lateinit var btn_ArmaPropuesta : Button
    var cargar = false
    var cliente : Cliente = Cliente()
    var urlImagenCLiente : String = ""

    private lateinit var viewModel: ViewModelDetalleReservaFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle_reserva, container, false)

        nombre = v.findViewById(R.id.textView_NombreCliente)
        fecha = v.findViewById(R.id.text_DatoFecha)
        hora = v.findViewById(R.id.text_DatoHora)
        direccion = v.findViewById(R.id.text_DatoUsuario)
        tipoCocina = v.findViewById(R.id.text_DatoTipoCocina)
        tieneHorno = v.findViewById(R.id.checkBox_TieneHorno)
        comensales = v.findViewById(R.id.text_DatoComensales)
        estiloCocina = v.findViewById(R.id.text_DatoEstiloCocina)
        tipoServicio = v.findViewById(R.id.text_DatoTipoServicio)
        notas = v.findViewById(R.id.text_DatoNotas)
        imagenCliente = v.findViewById(R.id.imageView_Cliente)
        btn_ArmaPropuesta = v.findViewById(R.id.btn_ArmaPropuesta)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelDetalleReservaFragment::class.java)

        viewModel.cliente.observe(viewLifecycleOwner, Observer { result ->

            cliente = result
            llenarDatos()
        })
    }

    override fun onStart() {
        super.onStart()

        reserva = DetalleReservaFragmentArgs.fromBundle(requireArguments()).argReserva

        viewModel.setBuscar(reserva.idUsuario)



        btn_ArmaPropuesta.setOnClickListener {
            val action =
                DetalleReservaFragmentDirections.actionDetalleReservaFragmentToFormularioPropuestaFragment(
                    reserva
                )
            v.findNavController().navigate(action)
        }
    }



    fun llenarDatos() {

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


        nombre.text = cliente.nombre
        fecha.text = reserva.fecha
        hora.text = reserva.hora
        direccion.text = reserva.direccion
        tipoCocina.text = reserva.tipoCocina
        GlideApp.with(this)
            .load(ref)
            .into(imagenCliente)

        if (reserva.tieneHorno.equals("true"))
            tieneHorno.setChecked(true)
        else
            tieneHorno.setChecked(false)

        comensales.text =  reserva.comensales.toString()
        estiloCocina.text = reserva.estiloCocina
        tipoServicio.text = reserva.tipoServicio
        notas.text = reserva.notas


    }

}