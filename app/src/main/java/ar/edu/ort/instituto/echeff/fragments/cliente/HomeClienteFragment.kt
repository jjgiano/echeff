package ar.edu.ort.instituto.echeff.fragments.cliente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ar.edu.ort.instituto.echeff.LoginActivity
import ar.edu.ort.instituto.echeff.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeClienteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var user: FirebaseUser
    lateinit var textViewUser: TextView
    lateinit var signoutButton: Button
    lateinit var v: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = HomeClienteFragmentArgs.fromBundle(requireArguments()).user!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_home_cliente, container, false)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeClienteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeClienteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}