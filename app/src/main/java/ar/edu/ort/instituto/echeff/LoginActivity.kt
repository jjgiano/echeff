package ar.edu.ort.instituto.echeff

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUserMetadata


class LoginActivity : AppCompatActivity() {
    val AUTH_REQUEST_CODE = 1234
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var listener: FirebaseAuth.AuthStateListener
    lateinit var providers: List<AuthUI.IdpConfig>
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        setSharedPreferences()
        providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )
        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { p0 ->
            val user = p0.currentUser
            if (user != null) {
                var isNew = false
                val metadata: FirebaseUserMetadata = user.metadata!!
                if ((metadata.creationTimestamp == metadata.lastSignInTimestamp) && !sharedPreferences.contains("isNew")) {
                    isNew = true
                }
                editor.putString("userId", user.uid)
                editor.putString("userDisplayName", user.displayName)
                editor.putString("userEmail", user.email)
                editor.putBoolean("isNew", isNew)
                editor.putBoolean("onBoardingFinished", true)
                editor.commit()
                //Already singned-in
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.ic_chef_hat)
                        .setTheme(R.style.LogInTheme)
                        .setAvailableProviders(providers)
                        .build(), AUTH_REQUEST_CODE
                )
            }
        }
    }

    private fun setSharedPreferences() {
        sharedPreferences = getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        if(!sharedPreferences.contains("userId")) {
            editor.clear().apply()
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        firebaseAuth.removeAuthStateListener(listener)
        super.onStop()
    }

}