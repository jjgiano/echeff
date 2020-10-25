package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ComentariosListAdapter
import ar.edu.ort.instituto.echeff.entities.Comentario
import com.bumptech.glide.Glide


class PerfilChefComentariosFragment : Fragment() {
    lateinit var v: View
    lateinit var imgChefPerfil: ImageView
    lateinit var lblNombreChef: TextView
    lateinit var lblCantidadMeGusta: TextView
    lateinit var lblCantidadComentarios: TextView
    lateinit var revComentarioCliente: RecyclerView

    private var comentariosFakeList: MutableList<Comentario> = ArrayList()

    private lateinit var comentariosListAdapter: ComentariosListAdapter
    private lateinit var linearLayoutManagerComentario: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        comentariosFakeList.add(
            Comentario(
                1,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )
        comentariosFakeList.add(
            Comentario(
                2,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )
        comentariosFakeList.add(
            Comentario(
                3,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )
        comentariosFakeList.add(
            Comentario(
                4,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )
        comentariosFakeList.add(
            Comentario(
                5,
                "Espectacular la comida!! super recomendado.",
                3,
                "https://www.inmosenna.com/wp-content/uploads/2018/07/avatar-user-teacher-312a499a08079a12-512x512-300x300.png"
            )
        )
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
        linearLayoutManagerComentario = LinearLayoutManager(context)
        revComentarioCliente.setHasFixedSize(true)
        revComentarioCliente.layoutManager = linearLayoutManagerComentario

        lblNombreChef.text = getString(R.string.nombre_chef, "Santiago Moras Mom ")
        lblCantidadComentarios.text = getString(R.string.cantidad_comentarios_chef, 5)
        lblCantidadMeGusta.text = getString(R.string.cantidad_megusta_chef, 7)
        Glide.with(this)
            .load("https://sumicorp.com/wp-content/uploads/2018/10/user.png")
            .centerInside()
            .into(imgChefPerfil);


        comentariosListAdapter = ComentariosListAdapter(comentariosFakeList, requireContext())
        revComentarioCliente.adapter = comentariosListAdapter
    }
}