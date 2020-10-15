package ar.edu.ort.instituto.echeff.fragments.onboarding.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R


class SecondScreenFragment : Fragment() {

    lateinit var v: View
    lateinit var btnComenzar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_second_screen, container, false)

        btnComenzar = v.findViewById(R.id.btnComenzar)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnComenzar.setOnClickListener() {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean("Finished", true)
        editor.apply()
    }
}