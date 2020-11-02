package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Comentario
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelFormularioPropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefConfiguracionFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasConfirmarFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PerfilChefConfiguracionFragment : Fragment() {

    private lateinit var viewModel: ViewModelPerfilChefConfiguracionFragment
    lateinit var v: View
    lateinit var btnAgregarFotoPerfil: Button
    lateinit var txaBiografia: TextView
    lateinit var lblContadorBio: TextView
    lateinit var btnGuardarBiografia: Button
    lateinit var btnAgregarFotoHistoria: Button
    lateinit var txaAgregarComentarioHistoria: TextView
    lateinit var lblContadorComentarioHistoria: TextView
    lateinit var btnAgregarHistoria: Button
    lateinit var btn_EditarPerfil : FloatingActionButton


    lateinit var perfil : PerfilChef
    lateinit var historia : Historia

    var buscar = false
    var nuevo = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo conectar con firebase
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_perfil_chef_configuracion, container, false)

        btnAgregarFotoPerfil = v.findViewById(R.id.btnAgregarFotoPerfil)
        txaBiografia = v.findViewById(R.id.txaBiografia)
        lblContadorBio = v.findViewById(R.id.lblContadorBio)
        btnGuardarBiografia = v.findViewById(R.id.btnGuardarBiografia)
        btnAgregarFotoHistoria = v.findViewById(R.id.btnAgregarFotoHistoria)
        txaAgregarComentarioHistoria = v.findViewById(R.id.txaAgregarComentarioHistoria)
        lblContadorComentarioHistoria = v.findViewById(R.id.lblContadorComentarioHistoria)
        btnAgregarHistoria = v.findViewById(R.id.btnAgregarHistoria)
        btn_EditarPerfil = v.findViewById(R.id.fab_editar)

        btnGuardarBiografia.setVisibility(View.INVISIBLE);

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelPerfilChefConfiguracionFragment::class.java)

        viewModel.dataPerfil.observe(viewLifecycleOwner, Observer { result ->

            perfil = result
            if (perfil.id.equals("")) {
                nuevo = true
            }

            llenarFichaPerfil()
        })
    }
    override fun onStart() {
        super.onStart()

        var sharedPref: SharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        var idUsuario : String  = sharedPref.getString("userId","Vacio")!!
        var nombreUsuario : String = sharedPref.getString("userDisplayName","Nombre No encontrado")!!

        viewModel.setBuscar(idUsuario)

        btn_EditarPerfil.setOnClickListener(){
            txaBiografia.setFocusable(true)
            txaBiografia.setEnabled(true)
            btnGuardarBiografia.setVisibility(View.VISIBLE);
        }

        btnAgregarFotoHistoria.setOnClickListener(){
            //todo agregar funcionalidad
        }

        btnAgregarFotoPerfil.setOnClickListener(){
            //todo agregar funcionalidad
        }

        btnGuardarBiografia.setOnClickListener(){
            perfil.bio = txaBiografia.text.toString()
            if (nuevo) {
                perfil.meGusta = 0
                perfil.idChef = idUsuario
                viewModel.agregarPerfil(perfil)
            } else {
                viewModel.actualizarPerfil(perfil)
            }

        }

        btnAgregarHistoria.setOnClickListener(){
            //todo agregar funcionalidad
        }
    }

    fun llenarFichaPerfil(){

        txaBiografia.text = perfil.bio
        lblContadorBio.text = perfil.bio.length.toString()


    }
}