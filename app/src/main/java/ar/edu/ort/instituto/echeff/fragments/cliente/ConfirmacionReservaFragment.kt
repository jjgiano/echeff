package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfirmacionReservaFragment : Fragment() {
    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewConfirmaReserva: TextView
    lateinit var textViewNombreChef: TextView
    lateinit var imageViewChef: ImageView
    lateinit var textViewTituloSnack: TextView
    lateinit var textViewSnack: TextView
    lateinit var textViewTituloEntrada: TextView
    lateinit var textViewEntrada: TextView
    lateinit var textViewTituloPlatoPrincipal: TextView
    lateinit var textViewPlatoPrincipal: TextView
    lateinit var textViewTituloPostre: TextView
    lateinit var textViewPostre: TextView
    lateinit var textViewTituloAdicionales: TextView
    lateinit var textViewAdicionales: TextView
    lateinit var textViewTituloImporteTotal: TextView
    lateinit var textViewImporteTotal: TextView
    lateinit var textViewTituloPorComensal: TextView
    lateinit var buttonModificar: Button
    lateinit var buttonConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_confirmacion_reserva, container, false)

        textViewConfirmaReserva = v.findViewById(R.id.textViewConfirmaReserva)
        textViewNombreChef = v.findViewById(R.id.textViewNombreChef)
        imageViewChef = v.findViewById(R.id.imageViewChef)
        textViewTituloSnack = v.findViewById(R.id.textViewTituloSnack)
        textViewSnack = v.findViewById(R.id.textViewSnack)
        textViewTituloEntrada = v.findViewById(R.id.textViewTituloEntrada)
        textViewEntrada = v.findViewById(R.id.textViewEntrada)
        textViewTituloPlatoPrincipal = v.findViewById(R.id.textViewTituloPlatoPrincipal)
        textViewPlatoPrincipal = v.findViewById(R.id.textViewPlatoPrincipal)
        textViewTituloPostre = v.findViewById(R.id.textViewTituloPostre)
        textViewPostre = v.findViewById(R.id.textViewPostre)
        textViewTituloAdicionales = v.findViewById(R.id.textViewTituloAdicionales)
        textViewAdicionales = v.findViewById(R.id.textViewAdicionales)
        textViewTituloImporteTotal = v.findViewById(R.id.textViewTituloImporteTotal)
        textViewImporteTotal = v.findViewById(R.id.textViewImporteTotal)
        textViewTituloPorComensal = v.findViewById(R.id.textViewTituloPorComensal)
        buttonModificar = v.findViewById(R.id.buttonModificar)
        buttonConfirmar = v.findViewById(R.id.buttonConfirmar)

        return v
    }

    override fun onStart() {
        super.onStart()

        buttonModificar.setOnClickListener {
            Snackbar.make(it, "buttonModificar.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

        buttonConfirmar.setOnClickListener {
            Snackbar.make(it, "buttonConfirmar.setOnClickListener", Snackbar.LENGTH_SHORT).show()
        }

    }

}