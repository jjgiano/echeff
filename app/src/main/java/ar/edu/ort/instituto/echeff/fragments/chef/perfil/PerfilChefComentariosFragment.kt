package ar.edu.ort.instituto.echeff.fragments.chef.perfil


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ComentariosListAdapter
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.entities.Puntuacion
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefFragment
import ar.edu.ort.instituto.echeff.utils.GlideApp
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles


class PerfilChefComentariosFragment : Fragment(), StorageReferenceUtiles {
    lateinit var v: View

    private lateinit var viewModel: ViewModelPerfilChefFragment
    lateinit var imgChefPerfil: ImageView
    lateinit var lblNombreChef: TextView
    lateinit var lblCantidadMeGusta: TextView
    lateinit var lblCantidadComentarios: TextView
    lateinit var revComentarioCliente: RecyclerView

    var chef = Chef()
    var perfil = PerfilChef()

    private var puntuacionList: MutableList<Puntuacion> = ArrayList()

    private lateinit var puntuacionListAdapter: ComentariosListAdapter
    private lateinit var linearLayoutManagerComentario: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_perfil_chef_comentarios, container, false)

        imgChefPerfil = v.findViewById(R.id.imgChefPerfil)
        lblNombreChef = v.findViewById(R.id.lblNombreChef)
        lblCantidadMeGusta = v.findViewById(R.id.lblCantidadMeGusta)
        lblCantidadComentarios = v.findViewById(R.id.lblCantidadComentarios)
        revComentarioCliente = v.findViewById(R.id.revComentarioCliente)

        return v
    }

    override fun onStart() {
        super.onStart()

        chef = PerfilChefComentariosFragmentArgs.fromBundle(requireArguments()).ChefComentarioArg

        viewModel.setBuscar(chef.idUsuario)

        linearLayoutManagerComentario = LinearLayoutManager(context)
        revComentarioCliente.setHasFixedSize(true)
        revComentarioCliente.layoutManager = linearLayoutManagerComentario

       llenarDatosChef()



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelPerfilChefFragment::class.java)


        viewModel.listDataPuntuacion.observe(viewLifecycleOwner, Observer { puntuaciones ->
            puntuacionList = puntuaciones

            puntuacionListAdapter = ComentariosListAdapter(puntuacionList, requireContext())
            revComentarioCliente.adapter = puntuacionListAdapter
        })

        viewModel.dataPerfil.observe(viewLifecycleOwner, Observer { result ->

            perfil = result
        })

        viewModel.chef.observe(viewLifecycleOwner, Observer {result ->
            chef = result
            llenarDatosChef()
        })
    }


    private fun llenarDatosChef(){
            lblNombreChef.text = chef.nombre
            lblCantidadComentarios.text = puntuacionList.size.toString()
            lblCantidadMeGusta.text = perfil.meGusta.toString()

            GlideApp
                .with(this)
                .load(buscarReferencia(chef.urlFoto))
                .centerInside()
                .into(imgChefPerfil)

        }

}