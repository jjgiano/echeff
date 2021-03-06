package ar.edu.ort.instituto.echeff.fragments.chef.perfil


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
import ar.edu.ort.instituto.echeff.adapters.HistoriasListAdapter
import ar.edu.ort.instituto.echeff.entities.*
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
    lateinit var btn_backHome : FloatingActionButton

    //Datos de la Puntiacion
    lateinit var imgUsuario : ImageView
    lateinit var txtComentarioCliente : TextView
    lateinit var txtCalificacionCliente: TextView

    lateinit var btnAgregarMeGusta: Button

    private var puntuacionList: MutableList<Puntuacion> = ArrayList()
    private var historiasList: MutableList<Historia> = ArrayList()

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
        btnVerMasComentarios = v.findViewById(R.id.btnVerMasComentarios)
        revHistoriasChef = v.findViewById(R.id.revHisotriasChef)
        btnConfiguracionPerfilChef = v.findViewById(R.id.fabConfiguracion)
        btnHistoriasPerfilChef = v.findViewById(R.id.fabHistorias)
        btnFloatMenu = v.findViewById(R.id.floatmenu)
        btnAgregarMeGusta = v.findViewById(R.id.btnAgregarMeGusta)
        imgUsuario = v.findViewById(R.id.imgHistoria)
        txtComentarioCliente = v.findViewById(R.id.txtComentarioCliente)
        txtCalificacionCliente = v.findViewById(R.id.txtCalificacionCliente)
        btn_backHome = v.findViewById(R.id.fab_backHome)

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
        viewModel.listDataPuntuacion.observe(viewLifecycleOwner, Observer { puntuaciones ->
            puntuacionList = puntuaciones
            llenarPuntuacion()
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
        linearLayoutManagerHistoria = LinearLayoutManager(context)
        revHistoriasChef.isNestedScrollingEnabled = false
        revHistoriasChef.layoutManager = linearLayoutManagerHistoria


        //Listeners
        btnVerMasComentarios.setOnClickListener() {
            val perfilComentarioChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefComentariosFragment(chef)
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

        if (eschef) {
            btn_backHome.setOnClickListener() {
                val perfilConfiguracionChef =
                    PerfilChefFragmentDirections.actionPerfilChefFragmentToHomeChefFragment();
                v.findNavController().navigate(perfilConfiguracionChef)
            }
        }else{
            btn_backHome.visibility = View.GONE
        }
    }

   private fun onItemClick(position: Int) {
        val irAHistoria =
            PerfilChefFragmentDirections.actionPerfilChefFragmentToDetalleHistoriaFragment(historiasList[position])
        v.findNavController().navigate(irAHistoria);
    }

    private fun llenarFichaPerfil(){
        txtBiografiaChef.text = perfil.bio
        lblCantidadComentarios.text = puntuacionList.size.toString()
        lblCantidadMeGusta.text = perfil.meGusta.toString()

        GlideApp
            .with(this)
            .load(buscarReferencia(chef.urlFoto))
            .centerInside()
            .into(imgChefPerfil);

    }

    private fun llenarPuntuacion(){
        if (puntuacionList.size > 0) {
            var item = puntuacionList.size - 1

            txtComentarioCliente.text = puntuacionList[item].mensaje
            txtCalificacionCliente.text =
                "Puntuacion: " + puntuacionList[item].idPuntuacion.toString()

            GlideApp
                .with(this)
                .load(buscarReferencia(""))
                .centerInside()
                .into(imgUsuario);
        } else {
            txtComentarioCliente.text = "SIN COMENTARIOS"
            txtCalificacionCliente.text =
                "Puntuacion: " + 0

            GlideApp
                .with(this)
                .load(buscarReferencia(""))
                .centerInside()
                .into(imgUsuario);
        }

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }




}