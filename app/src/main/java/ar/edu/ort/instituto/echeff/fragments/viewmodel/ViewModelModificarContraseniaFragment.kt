package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch

class ViewModelModificarContraseniaFragment : ViewModel(), UsuarioDao {

    fun changePassword(pass: String, passOld : String) :String {
        var error = ""
        viewModelScope.launch {
            try {
                cambiarPassword(pass, passOld)
            } catch (e: Error) {
                error = e.message.toString()
            }
        }
        return error
    }
}