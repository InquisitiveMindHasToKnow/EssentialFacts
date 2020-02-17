package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val binding = DataBindingUtil.inflate<MainPageFragmentBinding>(inflater, R.layout.main_page_fragment, container, false)

        binding.playButton.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_mainPageFragment_to_triviaFragment))

        return binding.root
    }

}



