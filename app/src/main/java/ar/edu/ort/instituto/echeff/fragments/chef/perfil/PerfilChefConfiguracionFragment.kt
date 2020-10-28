package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ar.edu.ort.instituto.echeff.R


class PerfilChefConfiguracionFragment : Fragment() {
    lateinit var v: View
    lateinit var btnAgregarFotoPerfil: Button
    lateinit var txaBiografia: EditText
    lateinit var lblContadorBio: TextView
    lateinit var btnGuardarBiografia: Button
    lateinit var btnAgregarFotoHistoria: Button
    lateinit var txaAgregarComentarioHistoria: EditText
    lateinit var lblContadorComentarioHistoria: TextView
    lateinit var btnAgregarHistoria: Button

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

        return v
    }

    override fun onStart() {
        super.onStart()

        btnAgregarFotoHistoria.setOnClickListener(){
            //todo agregar funcionalidad
        }

        btnAgregarFotoPerfil.setOnClickListener(){
            //todo agregar funcionalidad
        }

        btnGuardarBiografia.setOnClickListener(){
            //todo agregar funcionalidad
        }

        btnAgregarHistoria.setOnClickListener(){
            //todo agregar funcionalidad
        }
    }
}