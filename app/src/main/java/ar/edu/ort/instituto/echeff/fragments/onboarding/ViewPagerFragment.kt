package ar.edu.ort.instituto.echeff.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.adapters.ViewPagerAdapter
import ar.edu.ort.instituto.echeff.fragments.onboarding.screen.FirstScreenFragment
import ar.edu.ort.instituto.echeff.fragments.onboarding.screen.SecondScreenFragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*


class ViewPagerFragment : Fragment() {
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        v.viewPager.adapter = adapter
        return v;
    }

}