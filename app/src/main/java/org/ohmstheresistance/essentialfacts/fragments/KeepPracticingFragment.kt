package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.KeepPracticingFragmentBinding

class KeepPracticingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<KeepPracticingFragmentBinding>(
            inflater,
            R.layout.keep_practicing_fragment,
            container,
            false
        )
        var keepPracticingInfoArgs = arguments?.let { KeepPracticingFragmentArgs.fromBundle(it) }

        binding.gameOverTextTextview.setText(getString(R.string.not_all_answers_correct,
            keepPracticingInfoArgs?.numCorrect, keepPracticingInfoArgs?.numQuestions))


        binding.tryAgainButton.setOnClickListener { view: View ->

            view.findNavController()
                .navigate(KeepPracticingFragmentDirections.actionKeepPracticingFragmentToTriviaFragment())
        }

        return binding.root
    }
}
