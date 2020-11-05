package ar.edu.ort.instituto.echeff.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class InicioFragment : Fragment() {
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (onBoardingFinished()) {
                    val loginScreen =
                        InicioFragmentDirections.actionInicioFragmentToLoginActivity();
                    findNavController().navigate(loginScreen)
                } else {
                    val nextScreen =
                        InicioFragmentDirections.actionInicioFragmentToViewPagerFragment()
                    findNavController().navigate(nextScreen)

                }
            }, 3000
        )
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPref.getBoolean("onBoardingFinished", false)
    }
}