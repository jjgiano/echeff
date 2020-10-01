package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.edu.ort.instituto.echeff.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VistaPropuestasFragment : Fragment() {

    val db = Firebase.firestore
    lateinit var v: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_vista_propuestas, container, false)

        return v
    }

}