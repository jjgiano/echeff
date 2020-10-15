package ar.edu.ort.instituto.echeff.fragments.onboarding.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import ar.edu.ort.instituto.echeff.R
import com.bumptech.glide.Glide


class SecondScreenFragment : Fragment() {

    lateinit var v: View
    lateinit var btnComenzar: Button
    lateinit var imgSecondScreenBackGround: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_second_screen, container, false)

        imgSecondScreenBackGround = v.findViewById(R.id.imgSecondScreenBackGround)
        btnComenzar = v.findViewById(R.id.btnComenzar)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/backgroundSecondScreen.png?alt=media&token=886445cd-5f77-4a1d-8ada-eceac8ee9f30")
            .into(imgSecondScreenBackGround)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnComenzar.setOnClickListener() {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean("Finished", true)
        editor.apply()
    }
}