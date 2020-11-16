package ar.edu.ort.instituto.echeff.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.instituto.echeff.dao.UsuarioDao
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModelModificarContraseniaFragment : ViewModel(), UsuarioDao {

    fun changePassword(pass: String, passOld : String) {
        var error = ""
        viewModelScope.launch {
        try {
            cambiarPassword(pass, passOld)
        }catch (e : Error) {
            throw  Error( e.message.toString())

        }

        }

    }
}