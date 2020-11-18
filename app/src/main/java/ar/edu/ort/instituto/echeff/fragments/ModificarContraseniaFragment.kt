package ar.edu.ort.instituto.echeff.fragments

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.fragments.viewmodel.ViewModelModificarContraseniaFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.validator.Validator
import com.google.android.material.snackbar.Snackbar

class ModificarContraseniaFragment : Fragment(), Validator {

    private lateinit var viewModel: ViewModelModificarContraseniaFragment
    lateinit var v: View
    lateinit var txOldPass: TextView
    lateinit var txNewPass: TextView
    lateinit var txRepitPass: TextView
    lateinit var bt_ModificaPass: Button
    lateinit var sharedPreferences : SharedPreferences

    var mensaje = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_modificar__contrasenia, container, false)

        txOldPass = v.findViewById(R.id.txPassOld)
        txNewPass = v.findViewById(R.id.txNewPass)
        txRepitPass = v.findViewById(R.id.txRepitePass)
        bt_ModificaPass = v.findViewById(R.id.btn_ModificarContraseña)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelModificarContraseniaFragment::class.java)

    }

    override fun onStart() {
        super.onStart()
        clearErrors()
        setSharedPreferences()

        var eschef : Boolean = sharedPreferences.getBoolean("isChef",false)


        bt_ModificaPass.setOnClickListener {

            if (validar(1)) {
                try {
                    viewModel.changePassword(txRepitPass.text.toString(), txOldPass.text.toString())
                } catch (e: Error) {
                    mensaje = e.message.toString()
                }

                if (mensaje.equals("")) Snackbar.make(
                    it,
                    "Contraseña actualizada!!!",
                    Snackbar.LENGTH_LONG
                ).show()
                else Snackbar.make(it, mensaje, Snackbar.LENGTH_LONG).show()

                txOldPass.text=""
                txNewPass.text=""
                txRepitPass.text = ""

            }
            var action : NavDirections? = null
            if (eschef)  action = ModificarContraseniaFragmentDirections.actionModificarContraseniaFragmentToHomeChefFragment()
            else action = ModificarContraseniaFragmentDirections.actionModificarContraseniaFragmentToHomeClienteFragment()

            v.findNavController().navigate(action)
        }
    }

    private fun validar(opc: Int): Boolean {
        var valido = true
        clearErrors()
        if (opc == 1) {
            try {
                validarLargoDeContrasenia(txOldPass.text.toString())
            } catch (e: Error) {
                txOldPass.error = e.message
                valido = false
            }
            try {
                validarLargoDeContrasenia(txNewPass.text.toString())
            } catch (e: Error) {
                txNewPass.error = e.message
                valido = false
            }
            try {
                validarLargoDeContrasenia(txRepitPass.text.toString())
            } catch (e: Error) {
                txRepitPass.error = e.message
                valido = false
            }
            if (!txNewPass.text.toString().equals(txRepitPass.text.toString())) {
                txRepitPass.error = "Las contraseña debe ser Iguales"
                valido = false
            }

        }
        return valido
    }

    private fun clearErrors() {
        txOldPass.error = null
        txNewPass.error = null
        txRepitPass.error = null
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

}