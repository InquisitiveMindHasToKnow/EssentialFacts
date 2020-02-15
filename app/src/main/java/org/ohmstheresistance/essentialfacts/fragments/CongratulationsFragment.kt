package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.CongratulationsFragmentBinding

class CongratulationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<CongratulationsFragmentBinding>(
            inflater,
            R.layout.congratulations_fragment,
            container,
            false
        )

        var winningInfoArgs = arguments?.let { CongratulationsFragmentArgs.fromBundle(it) }

        binding.winnerTextTextview.setText(getString(R.string.all_answers_correct,
            winningInfoArgs?.numCorrect, winningInfoArgs?.numQuestions))


        binding.nextMatchButton.setOnClickListener { view: View ->

            view.findNavController()
                .navigate(CongratulationsFragmentDirections.actionCongratulationsFragmentToTriviaFragment())
        }

        return binding.root
    }
}
