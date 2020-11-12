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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ComentariosListAdapter
import ar.edu.ort.instituto.echeff.adapters.HistoriasListAdapter
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Comentario
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefFragment
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu



class PerfilChefFragment : Fragment(), StorageReferenceUtiles {
    lateinit var v: View
    private lateinit var viewModel: ViewModelPerfilChefFragment
    lateinit var sharedPreferences: SharedPreferences

    lateinit var imgChefPerfil: ImageView
    lateinit var lblNombreChef: TextView
    lateinit var lblCantidadMeGusta: TextView
    lateinit var lblCantidadComentarios: TextView
    lateinit var txtBiografiaChef: TextView
    lateinit var revComentarioCliente: RecyclerView
    lateinit var revHistoriasChef: RecyclerView
    lateinit var btnVerMasComentarios: Button
    lateinit var btnFloatMenu : FloatingActionsMenu
    lateinit var btnHistoriasPerfilChef: FloatingActionButton
    lateinit var btnConfiguracionPerfilChef: FloatingActionButton

    lateinit var btnAgregarMeGusta: Button

    private var comentariosFakeList: MutableList<Comentario> = ArrayList()
    private var historiasList: MutableList<Historia> = ArrayList()

    private lateinit var comentariosListAdapter: ComentariosListAdapter
    private lateinit var linearLayoutManagerComentario: LinearLayoutManager

    private lateinit var historiasListAdapter: HistoriasListAdapter
    private lateinit var linearLayoutManagerHistoria: LinearLayoutManager

    lateinit var perfil: PerfilChef
    var chef = Chef()


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
        btnConfiguracionPerfilChef = v.findViewById(R.id.fabConfiguracion)
        btnHistoriasPerfilChef = v.findViewById(R.id.fabHistorias)
        btnFloatMenu = v.findViewById(R.id.floatmenu)
        btnAgregarMeGusta = v.findViewById(R.id.btnAgregarMeGusta)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelPerfilChefFragment::class.java)

        viewModel.dataPerfil.observe(viewLifecycleOwner, Observer { result ->

            perfil = result
        })

        viewModel.chef.observe(viewLifecycleOwner, Observer {result ->
            chef = result
            llenarFichaPerfil()
        })

        viewModel.listDataHistoria.observe(viewLifecycleOwner, Observer{ historias ->
            historiasList = historias
            historiasListAdapter = HistoriasListAdapter(historiasList,   requireContext()) { position -> onItemClick(position) }
            revHistoriasChef.adapter = historiasListAdapter
        })
    }


    override fun onStart() {
        super.onStart()
        setSharedPreferences()

        var idUsuario : String  = sharedPreferences.getString("userId","Vacio")!!
        var nombreUsuario : String = sharedPreferences.getString("userDisplayName","Nombre No encontrado")!!
        var eschef : Boolean = sharedPreferences.getBoolean("isChef",false)

        if (!eschef) {
            btnFloatMenu.visibility = View.INVISIBLE
            chef = PerfilChefFragmentArgs.fromBundle(requireArguments()).argChef
            lblNombreChef.text =chef.nombre
            viewModel.setBuscar(chef.idUsuario)

        } else {
            lblNombreChef.text = nombreUsuario
            viewModel.setBuscar(idUsuario)
        }
        //Configuracion
        linearLayoutManagerComentario = LinearLayoutManager(context)
        revComentarioCliente.setHasFixedSize(true)
        revComentarioCliente.layoutManager = linearLayoutManagerComentario

        linearLayoutManagerHistoria = LinearLayoutManager(context)
        revHistoriasChef.isNestedScrollingEnabled = false
        revHistoriasChef.layoutManager = linearLayoutManagerHistoria



        comentariosListAdapter = ComentariosListAdapter(comentariosFakeList, requireContext())
        revComentarioCliente.adapter = comentariosListAdapter



        //Listeners
        btnVerMasComentarios.setOnClickListener() {
            val perfilComentarioChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefComentariosFragment()
            v.findNavController().navigate(perfilComentarioChef)
        }

        btnAgregarMeGusta.setOnClickListener() {
            if (!eschef) perfil.meGusta += 1;
            lblCantidadMeGusta.text = perfil.meGusta.toString()
            viewModel.actualizarPerfil(perfil)
        }

        btnHistoriasPerfilChef.setOnClickListener() {
            val perfilConfiguracionChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefConfiguracionFragment();
            v.findNavController().navigate(perfilConfiguracionChef)
        }
        btnConfiguracionPerfilChef.setOnClickListener() {
            val perfilConfiguracionChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToConfiguracionUsuarioFragment2();
            v.findNavController().navigate(perfilConfiguracionChef)
        }
    }

   private fun onItemClick(position: Int) {
        val irAHistoria =
            PerfilChefFragmentDirections.actionPerfilChefFragmentToDetalleHistoriaFragment(historiasList[position])
        v.findNavController().navigate(irAHistoria);
    }

    fun llenarFichaPerfil(){
        txtBiografiaChef.text = perfil.bio
        lblCantidadComentarios.text = 5.toString()
        lblCantidadMeGusta.text = perfil.meGusta.toString()

        GlideApp
            .with(this)
            .load(buscarReferencia(chef.urlFoto))
            .centerInside()
            .into(imgChefPerfil);

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }




}