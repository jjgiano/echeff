package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ComentariosListAdapter
import ar.edu.ort.instituto.echeff.adapters.HistoriasListAdapter
import ar.edu.ort.instituto.echeff.entities.Comentario
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.fragments.chef.perfil.PerfilChefFragmentDirections
import com.bumptech.glide.Glide

class PerfilChefFragment : Fragment() {
    lateinit var v: View
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Todo conectar con firebase
        comentariosFakeList.add(
            Comentario(
                1,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )

        historiasFakeList.add(
            Historia(
                1,
                "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/Mask%20Group.png?alt=media&token=c3346e09-eb96-4b12-8686-136b39c9f542",
                "Historia numero 1",
                34
            )
        )
        historiasFakeList.add(
            Historia(
                2,
                "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/Mask%20Group.png?alt=media&token=c3346e09-eb96-4b12-8686-136b39c9f542",
                "Historia numero 2",
                15
            )
        )
        historiasFakeList.add(
            Historia(
                3,
                "https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/Mask%20Group.png?alt=media&token=c3346e09-eb96-4b12-8686-136b39c9f542",
                "Historia numero 3",
                500
            )
        )
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

    override fun onStart() {
        super.onStart()

        //Configuracion
        linearLayoutManagerComentario = LinearLayoutManager(context)
        revComentarioCliente.setHasFixedSize(true)
        revComentarioCliente.layoutManager = linearLayoutManagerComentario

        linearLayoutManagerHistoria = LinearLayoutManager(context)
        revHistoriasChef.isNestedScrollingEnabled = false
        revHistoriasChef.layoutManager = linearLayoutManagerHistoria

        var cantidadDeMegusta = 7
        val bio =
            "Desde muy niño, Santiago Moras Mom ha incursionado en la confeccion de Carnes Rojas. \"Asar carne a la parrilla requiere conocer los tiempos de asado de cada carne y por ende su orden de cocción.\" Estudio para ser Cheff para poder dedicarle su vida a lo que mas le gusta hacer, y lo comparte con todos los comensales."
        txtBiografiaChef.text = getString(R.string.biografia_chef, bio)
        lblNombreChef.text = getString(R.string.nombre_chef, "Santiago Moras Mom ")
        lblCantidadComentarios.text = getString(R.string.cantidad_comentarios_chef, 5)
        lblCantidadMeGusta.text = getString(R.string.cantidad_megusta_chef, cantidadDeMegusta)
        Glide.with(this)
            .load("https://sumicorp.com/wp-content/uploads/2018/10/user.png")
            .centerInside()
            .into(imgChefPerfil);


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
            cantidadDeMegusta += 1;
            lblCantidadMeGusta.text = getString(R.string.cantidad_megusta_chef, cantidadDeMegusta)
        }

        btnConfiguracionPerfilChef.setOnClickListener() {
            val perfilConfiguracionChef =
                PerfilChefFragmentDirections.actionPerfilChefFragmentToPerfilChefConfiguracionFragment();
            v.findNavController().navigate(perfilConfiguracionChef)
        }
    }
}