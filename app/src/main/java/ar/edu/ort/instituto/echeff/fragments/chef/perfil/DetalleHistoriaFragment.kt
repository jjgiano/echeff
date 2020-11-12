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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.fragments.chef.home.DetalleReservaFragmentArgs
import ar.edu.ort.instituto.echeff.fragments.chef.home.DetalleReservaFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleHistoriaFragmetn
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles


class DetalleHistoriaFragment : Fragment() , StorageReferenceUtiles{

    lateinit var v :View
    lateinit var viewModel : ViewModelDetalleHistoriaFragmetn
    lateinit var txtComentario: TextView
    lateinit var imgHistoria: ImageView
    lateinit var txtMeGusta: TextView
    lateinit var btnMegusta : Button

    lateinit var historia : Historia
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_detalle_historia, container, false)
        txtComentario = v.findViewById(R.id.txtComentarioHistoria)
        imgHistoria = v.findViewById(R.id.imgHistoria)
        txtMeGusta = v.findViewById(R.id.txtCantidadLikeHistoria)
        btnMegusta = v.findViewById(R.id.btnMegusta)

        return v
    }

    override fun onStart() {
        super.onStart()

        setSharedPreferences()

        var eschef : Boolean = sharedPreferences.getBoolean("isChef",false)

        historia = DetalleHistoriaFragmentArgs.fromBundle(requireArguments()).argHistoria

        llenardatos()

       btnMegusta.setOnClickListener {
           if (!eschef) historia.cantidadMegusta += 1;
           txtMeGusta.text = historia.cantidadMegusta.toString()
           viewModel.actualizarHistoria(historia)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelDetalleHistoriaFragmetn::class.java)


    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun llenardatos() {

        txtMeGusta.text = historia.cantidadMegusta.toString()
        txtComentario.text = historia.comentario

        GlideApp.with(this)
            .load(buscarReferencia(historia.urlImagen))
            .centerInside()
            .into(imgHistoria)


    }


}