package ar.edu.ort.instituto.echeff.fragments.cliente

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
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.PropuestaDao
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ConfirmacionReservaFragment : Fragment(), PropuestaDao, UsuarioDao {
    val db = Firebase.firestore
    lateinit var v: View

    lateinit var sharedPreferences: SharedPreferences
    lateinit var propuesta: Propuesta
    lateinit var chef: Chef

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

        this.setSharedPreferences()

        // TODO: levantar la foto del chef del firebase
        Glide
            .with(super.requireContext())
            .load("https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/2_avatar-256.png")
            .centerInside()
            .into(imageViewChef)

        var idPropuesta = this.sharedPreferences.getString("idPropuesta","0")!!

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)
        scope.launch {
            propuesta = super.getPropuestaById(idPropuesta)
            //chef = super.getChefByUserId("W3ueDpWO46fo7u08mk9iwXKjQ902")
            // TODO: CAMBIAR LUEGO AL ID DE CHEF QUE ESTA EN LA PROPUESTA
            chef = super.getChefById(propuesta.idChef)
            textViewSnack.text = propuesta.snack
            textViewEntrada.text = propuesta.entrada
            textViewPlatoPrincipal.text = propuesta.plato
            textViewPostre.text = propuesta.postre
            textViewAdicionales.text = propuesta.adicional
            textViewImporteTotal.text = propuesta.total.toString()
            textViewNombreChef.text = chef.nombre
        }

        buttonModificar.setOnClickListener {
            sharedPreferences.edit().putString("idPropuesta", idPropuesta).apply()
            val modifcarPropuestaScreen = ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToModificacionReservaFragment()
            v.findNavController().navigate(modifcarPropuestaScreen)
        }

        buttonConfirmar.setOnClickListener {
            sharedPreferences.edit().putString("idPropuesta", idPropuesta).apply()
            val pagoScreen = ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToPagoReservaFragment()
            v.findNavController().navigate(pagoScreen)
        }

        crdPerfilChef.setOnClickListener {
            val perfilChefScreen = ConfirmacionReservaFragmentDirections.actionConfirmacionReservaFragment2ToPerfilChefFragment()
            v.findNavController().navigate(perfilChefScreen)
        }

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(EcheffUtilities.PREF_NAME.valor, AppCompatActivity.MODE_PRIVATE)
    }

}