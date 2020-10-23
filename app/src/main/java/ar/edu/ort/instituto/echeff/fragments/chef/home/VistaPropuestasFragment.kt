package ar.edu.ort.instituto.echeff.fragments.chef.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.VistaPropuestasAdapter
import ar.edu.ort.instituto.echeff.dao.PropuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.fragments.chef.home.VistaPropuestasFragmentDirections
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelVistaPropuestasFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VistaPropuestasFragment : Fragment(), PropuestasDao {

    val db = Firebase.firestore
    lateinit var v: View
    private lateinit var viewModel: ViewModelVistaPropuestasFragment
    var cargado : Boolean = false

    lateinit var textViewMisPropuestas: TextView
    lateinit var buttonTengoUnProblema: Button

    lateinit var textViewTituloPropuestasAConfirmar: TextView
    lateinit var textViewTituloPropuestasConfirmadas: TextView

    lateinit var rvPropuestasAConfirmar: RecyclerView
    lateinit var rvPropuestasConfirmadas: RecyclerView


    var propuestasAConfirmar: MutableList<Propuesta> = ArrayList<Propuesta>()
    var propuestasConfirmadas: MutableList<Propuesta> = ArrayList<Propuesta>()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelVistaPropuestasFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.listaLiveData.observe(viewLifecycleOwner, Observer { result ->

            propuestasAConfirmar = result

            rvPropuestasAConfirmar.setHasFixedSize(true)
            rvPropuestasAConfirmar.layoutManager = LinearLayoutManager(context)
            rvPropuestasAConfirmar.adapter = VistaPropuestasAdapter(propuestasAConfirmar, requireContext()){
                    position -> onItemAConfirmarClick(position)
            }
        })
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


        rvPropuestasAConfirmar = v.findViewById(R.id.rvPropuestasAConfirmar)
        rvPropuestasConfirmadas = v.findViewById(R.id.rvPropuestasConfirmadas)

        return v
    }


    override fun onStart() {
        super.onStart()
        //todo: cambier le 1 por el id del Usuario
        viewModel.setcargar("1")

        rvPropuestasConfirmadas.setHasFixedSize(true)
        rvPropuestasConfirmadas.layoutManager = LinearLayoutManager(context)
        rvPropuestasConfirmadas.adapter = VistaPropuestasAdapter(propuestasConfirmadas, requireContext()){
                position -> onItemConfirmadasClick(position)
        }


        buttonTengoUnProblema.setOnClickListener {
             var mesaAyudaScreen =
                 VistaPropuestasFragmentDirections.actionVistaPropuestasFragmentToMesaAyudaFragment2()
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



}