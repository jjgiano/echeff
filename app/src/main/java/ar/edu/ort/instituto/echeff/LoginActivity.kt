package ar.edu.ort.instituto.echeff

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val AUTH_REQUEST_CODE = 1234
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var listener: FirebaseAuth.AuthStateListener
    lateinit var providers: List<AuthUI.IdpConfig>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )
        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { p0 ->
            val user = p0.currentUser
            if (user != null) {
                val isNew = p0.pendingAuthResult?.result?.additionalUserInfo?.isNewUser
                //Already singned-in
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("user", user)
                //val extras: Bundle = Bundle()
                //extras.putString("user", user.displayName)
                //intent.putExtras(extras)
                startActivity(intent)
            } else {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.LogInTheme)
                        .setAvailableProviders(providers)
                        .build(), AUTH_REQUEST_CODE
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        if(listener != null) {
            firebaseAuth.removeAuthStateListener(listener)
        }
        super.onStop()
    }

}