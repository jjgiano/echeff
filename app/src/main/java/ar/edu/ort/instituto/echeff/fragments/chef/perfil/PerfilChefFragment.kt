package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ComentariosListAdapter
import ar.edu.ort.instituto.echeff.adapters.HistoriasListAdapter
import ar.edu.ort.instituto.echeff.entities.Comentario
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.fragments.chef.perfil.PerfilChefFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefConfiguracionFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.bumptech.glide.Glide

class PerfilChefFragment : Fragment() {
    lateinit var v: View
    private lateinit var viewModel: ViewModelPerfilChefFragment

    lateinit var imgChefPerfil: ImageView
    lateinit var lblNombreChef: TextView
    lateinit var lblCantidadMeGusta: TextView
    lateinit var lblCantidadComentarios: TextView
    lateinit var txtBiografiaChef: TextView
    lateinit var revComentarioCliente: RecyclerView
    lateinit var revHistoriasChef: RecyclerView
    lateinit var btnVerMasComentarios: Button
    lateinit var btnConfiguracionPerfilChef: Button
    lateinit var btnAgregarMeGusta: Button

    private var comentariosFakeList: MutableList<Comentario> = ArrayList()
    private var historiasFakeList: MutableList<Historia> = ArrayList()

    private lateinit var comentariosListAdapter: ComentariosListAdapter
    private lateinit var linearLayoutManagerComentario: LinearLayoutManager

    private lateinit var historiasListAdapter: HistoriasListAdapter
    private lateinit var linearLayoutManagerHistoria: LinearLayoutManager

    lateinit var perfil: PerfilChef


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_perfil_chef, container, false)

        imgChefPerfil = v.findViewById(R.id.imgChefPerfil)
        lblNombreChef = v.findViewById(R.id.lblNombreChef)
        lblCantidadMeGusta = v.findViewById(R.id.lblCantidadMeGusta)
        lblCantidadComentarios = v.findViewById(R.id.lblCantidadComentarios)
        txtBiografiaChef = v.findViewById(R.id.txtBiografiaChef)
        revComentarioCliente = v.findViewById(R.id.revComentarioCliente)
        btnVerMasComentarios = v.findViewById(R.id.btnVerMasComentarios)
        revHistoriasChef = v.findViewById(R.id.revHisotriasChef)
        btnConfiguracionPerfilChef = v.findViewById(R.id.btnConfiguracionPerfilChef)
        btnAgregarMeGusta = v.findViewById(R.id.btnAgregarMeGusta)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelPerfilChefFragment::class.java)

        viewModel.dataPerfil.observe(viewLifecycleOwner, Observer { result ->

            perfil = result


            llenarFichaPerfil()
        })
    }

    override fun onStart() {
        super.onStart()


        var sharedPref: SharedPreferences = requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor, Context.MODE_PRIVATE)
        var idUsuario : String  = sharedPref.getString("userId","Vacio")!!
        var nombreUsuario : String = sharedPref.getString("userDisplayName","Nombre No encontrado")!!


        lblNombreChef.text =nombreUsuario
        viewModel.setBuscar(idUsuario)

        //Configuracion
        linearLayoutManagerComentario = LinearLayoutManager(context)
        revComentarioCliente.setHasFixedSize(true)
        revComentarioCliente.layoutManager = linearLayoutManagerComentario

        linearLayoutManagerHistoria = LinearLayoutManager(context)
        revHistoriasChef.isNestedScrollingEnabled = false
        revHistoriasChef.layoutManager = linearLayoutManagerHistoria



        comentariosListAdapter = ComentariosListAdapter(comentariosFakeList, requireContext())
        revComentarioCliente.adapter = comentariosListAdapter

        historiasListAdapter = HistoriasListAdapter(historiasFakeList, requireContext())
        revHistoriasChef.adapter = historiasListAdapter

        //Listeners
        btnVerMasComentarios.setOnClickListener() {
            val perfilComentarioChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefComentariosFragment()
            v.findNavController().navigate(perfilComentarioChef)
        }

        btnAgregarMeGusta.setOnClickListener() {
            perfil.meGusta += 1;
            lblCantidadMeGusta.text = perfil.meGusta.toString()
            viewModel.actualizarPerfil(perfil)
        }

        btnConfiguracionPerfilChef.setOnClickListener() {
            val perfilConfiguracionChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefConfiguracionFragment();
            v.findNavController().navigate(perfilConfiguracionChef)
        }
    }

    fun llenarFichaPerfil(){

        txtBiografiaChef.text = perfil.bio

        lblCantidadComentarios.text = 5.toString()
        lblCantidadMeGusta.text = perfil.meGusta.toString()
        Glide.with(this)
            .load("https://sumicorp.com/wp-content/uploads/2018/10/user.png")
            .centerInside()
            .into(imgChefPerfil);



    }
}