package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.dao.propuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class VistaPropuestasFragment : Fragment(), propuestasDao {

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


        val scope = CoroutineScope(Dispatchers.Default )

        scope.launch {
            propuestasAConfirmar = getAll()

        }


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
            Snackbar.make(it, "Esto debe ir a la pantalla de la mesa de ayuda", Snackbar.LENGTH_SHORT).show()
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