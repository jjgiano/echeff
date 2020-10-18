package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.fragments.cliente.VistaReservasFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VistaPropuestasFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View

    lateinit var textViewMisPropuestas: TextView
    lateinit var buttonTengoUnProblema: Button

    lateinit var textViewTituloPropuestasAConfirmar: TextView
    lateinit var textViewTituloPropuestasConfirmadas: TextView
    lateinit var textViewTituloPropuestasFinalizadas: TextView

    lateinit var rvPropuestasAConfirmar: RecyclerView
    lateinit var rvPropuestasConfirmadas: RecyclerView
    lateinit var rvPropuestasFinalizadas: RecyclerView

    var propuestasAConfirmar: MutableList<Propuesta> = ArrayList<Propuesta>()
    var propuestasConfirmadas: MutableList<Propuesta> = ArrayList<Propuesta>()
    var propuestasFinalizadas: MutableList<Propuesta> = ArrayList<Propuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propuestasAConfirmar.add(Propuesta(1,"snack1", "entrada1", "plato1", "postre1", "adicional1", 100.1, 1, "1"))
        propuestasAConfirmar.add(Propuesta(2,"snack2", "entrada2", "plato2", "postre2", "adicional2", 100.2, 1, "2"))
        //propuestasAConfirmar.add(Propuesta(3,"snack3", "entrada3", "plato3", "postre3", "adicional3", 100.3, 1, 3))
        //propuestasAConfirmar.add(Propuesta(4,"snack4", "entrada4", "plato4", "postre4", "adicional4", 100.4, 1, 4))

        propuestasConfirmadas.add(Propuesta(5,"snack5", "entrada5", "plato5", "postre5", "adicional5", 100.5, 1, "5"))
        propuestasConfirmadas.add(Propuesta(6,"snack6", "entrada6", "plato6", "postre6", "adicional6", 100.6, 1, "6"))
        //propuestasConfirmadas.add(Propuesta(7,"snack7", "entrada7", "plato7", "postre7", "adicional7", 100.7, 1, 7))
        //propuestasConfirmadas.add(Propuesta(8,"snack8", "entrada8", "plato8", "postre8", "adicional8", 100.8, 1, 8))

        propuestasFinalizadas.add(Propuesta(9,"snack9", "entrada9", "plato9", "postre9", "adicional9", 100.9, 1, "9"))
        propuestasFinalizadas.add(Propuesta(10,"snack10", "entrada10", "plato10", "postre10", "adicional10", 100.10, 1, "10"))
        //propuestasFinalizadas.add(Propuesta(11,"snack11", "entrada11", "plato11", "postre11", "adicional11", 100.11, 1, 11))
        //propuestasFinalizadas.add(Propuesta(12,"snack12", "entrada12", "plato12", "postre12", "adicional12", 100.12, 1, 12))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_vista_propuestas, container, false)

        textViewMisPropuestas = v.findViewById(R.id.textViewMisPropuestas)
        buttonTengoUnProblema = v.findViewById(R.id.buttonTengoUnProblema)

        textViewTituloPropuestasAConfirmar = v.findViewById(R.id.textViewTituloPropuestasAConfirmar)
        textViewTituloPropuestasConfirmadas = v.findViewById(R.id.textViewTituloPropuestasConfirmadas)
        textViewTituloPropuestasFinalizadas = v.findViewById(R.id.textViewTituloPropuestasFinalizadas)

        rvPropuestasAConfirmar = v.findViewById(R.id.rvPropuestasAConfirmar)
        rvPropuestasConfirmadas = v.findViewById(R.id.rvPropuestasConfirmadas)
        rvPropuestasFinalizadas = v.findViewById(R.id.rvPropuestasFinalizadas)

        return v
    }

    override fun onStart() {
        super.onStart()
        rvPropuestasAConfirmar.setHasFixedSize(true)
        rvPropuestasAConfirmar.layoutManager = LinearLayoutManager(context)
        rvPropuestasAConfirmar.adapter = VistaPropuestasAdapter(propuestasAConfirmar, requireContext()){
                position -> onItemAConfirmarClick(position)
        }

        rvPropuestasConfirmadas.setHasFixedSize(true)
        rvPropuestasConfirmadas.layoutManager = LinearLayoutManager(context)
        rvPropuestasConfirmadas.adapter = VistaPropuestasAdapter(propuestasConfirmadas, requireContext()){
                position -> onItemConfirmadasClick(position)
        }

        rvPropuestasFinalizadas.setHasFixedSize(true)
        rvPropuestasFinalizadas.layoutManager = LinearLayoutManager(context)
        rvPropuestasFinalizadas.adapter = VistaPropuestasAdapter(propuestasFinalizadas, requireContext()){
                position -> onItemFinalizadasClick(position)
        }

        buttonTengoUnProblema.setOnClickListener {
            var mesaAyudaScreen = VistaPropuestasFragmentDirections.actionVistaPropuestasFragmentToMesaAyudaFragment2()
            v.findNavController().navigate(mesaAyudaScreen)
        }

    }

    private fun onItemAConfirmarClick(position : Int){
        val propuesta = propuestasAConfirmar[position]
        Snackbar.make(v, "ID de la propuesta: " + propuesta.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemConfirmadasClick(position : Int){
        val propuesta = propuestasConfirmadas[position]
        Snackbar.make(v, "ID de la propuesta: " + propuesta.id, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemFinalizadasClick(position : Int){
        val propuesta = propuestasFinalizadas[position]
        Snackbar.make(v, "ID de la propuesta: " + propuesta.id, Snackbar.LENGTH_SHORT).show()
    }

}