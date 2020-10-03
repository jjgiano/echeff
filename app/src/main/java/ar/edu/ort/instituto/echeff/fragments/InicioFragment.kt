package ar.edu.ort.instituto.echeff.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ar.edu.ort.instituto.echeff.LoginActivity
import ar.edu.ort.instituto.echeff.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Inicio1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Inicio1Fragment : Fragment() {
    lateinit var textViewUser: TextView
    lateinit var signoutButton: Button
    lateinit var v: View
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = (activity!!.intent.extras!!.get("user") as FirebaseUser?)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_inicio, container, false)
        textViewUser = v.findViewById(R.id.username)
        signoutButton = v.findViewById(R.id.signout)
        signoutButton.setOnClickListener {
            AuthUI.getInstance()
                .signOut(context!!)
                .addOnCompleteListener { // user is now signed out
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            //FirebaseAuth.getInstance().signOut()
            //val intent = Intent(this.activity, LoginActivity::class.java)
            //startActivity(intent)
        }
        textViewUser.text = user.displayName
        return v
    }

}