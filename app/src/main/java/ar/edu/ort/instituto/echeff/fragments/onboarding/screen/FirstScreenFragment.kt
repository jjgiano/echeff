package ar.edu.ort.instituto.echeff.fragments.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.instituto.echeff.R
import com.bumptech.glide.Glide

class FirstScreenFragment : Fragment() {

    lateinit var v: View
    lateinit var imgfirstScreenBackGround: ImageView
    lateinit var btnNextScreen: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_first_screen, container, false)

        imgfirstScreenBackGround = v.findViewById(R.id.imgfirstScreenBackGround)
        btnNextScreen = v.findViewById(R.id.btnNextScreen)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/pf2020-echeff.appspot.com/o/backgroundFirstScreen.png?alt=media&token=f7a79ed5-7aa7-4a82-aa4b-af78d4fc8693")
            .into(imgfirstScreenBackGround)

        return v
    }

    override fun onStart() {
        super.onStart()
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        btnNextScreen.setOnClickListener() {
            viewPager?.currentItem = 1
        }

    }
}