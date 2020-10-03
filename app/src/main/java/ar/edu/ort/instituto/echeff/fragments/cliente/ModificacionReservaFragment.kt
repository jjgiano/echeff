package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar


class ModificacionReservaFragment : Fragment() {

    lateinit var v: View
    private lateinit var btnEnviarModificaion: Button
    private lateinit var txtCambiosReserva: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_modificacion_reserva, container, false)

        btnEnviarModificaion =v.findViewById(R.id.btnEnviarCambios)
        txtCambiosReserva = v.findViewById(R.id.txtCambiosReserva)

        return v
    }


    override fun onStart() {
        super.onStart()

        //TODO
        btnEnviarModificaion.setOnClickListener(){
            Snackbar.make(v, "Se envio la modificacion al chef", Snackbar.LENGTH_SHORT).show()
        }
    }
}