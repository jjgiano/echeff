package ar.edu.ort.instituto.echeff.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfirmacionReservaFragment : Fragment() {
    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewNombreChef: TextView
    lateinit var imageViewChef: ImageView
    lateinit var textViewSnack: TextView
    lateinit var textViewEntrada: TextView
    lateinit var textViewPlatoPrincipal: TextView
    lateinit var textViewPostre: TextView
    lateinit var textViewAdicionales: TextView
    lateinit var textViewImporteTotal: TextView
    lateinit var buttonModificar: Button
    lateinit var buttonConfirmar: Button
    lateinit var crdPerfilChef: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_confirmacion_reserva, container, false)

        textViewNombreChef = v.findViewById(R.id.textViewNombreChef)
        imageViewChef = v.findViewById(R.id.imageViewChef)
        textViewSnack = v.findViewById(R.id.textViewSnack)
        textViewEntrada = v.findViewById(R.id.textViewEntrada)
        textViewPlatoPrincipal = v.findViewById(R.id.textViewPlatoPrincipal)
        textViewPostre = v.findViewById(R.id.textViewPostre)
        textViewAdicionales = v.findViewById(R.id.textViewAdicionales)
        textViewImporteTotal = v.findViewById(R.id.textViewImporteTotal)
        buttonModificar = v.findViewById(R.id.buttonModificar)
        buttonConfirmar = v.findViewById(R.id.buttonConfirmar)
        crdPerfilChef = v.findViewById(R.id.crdPerfilChef)

        return v
    }

    override fun onStart() {
        super.onStart()

        buttonModificar.setOnClickListener {
            val modifcarPropuestaScreen =
                ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToModificacionReservaFragment()
            v.findNavController().navigate(modifcarPropuestaScreen)
        }

        buttonConfirmar.setOnClickListener {
            val pagoScreen =
                ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToPagoReservaFragment()
            v.findNavController().navigate(pagoScreen)
        }

        crdPerfilChef.setOnClickListener {
            val perfilChefScreen =
                ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToPerfilChefFragment()
            v.findNavController().navigate(perfilChefScreen)
        }

    }

}