package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.TriviaFragmentBinding
import kotlin.math.min


class TriviaFragment : Fragment() {

    data class EssentialFactsTrivia(
        val question: String,
        val answers: List<String>
    )

    private val triviaQuestions: MutableList<EssentialFactsTrivia> = mutableListOf(
        EssentialFactsTrivia(
            question = "How many branches of government are there?",
            answers = listOf("Three", "One", "Two", "Four")
        ),
        EssentialFactsTrivia(
            question = "Who is the current Chief Justice of the U.S. Supreme Court?",
            answers = listOf("John Roberts", "George Bush", "Joe Biden", "Nancy Pelosi")
        ),
        EssentialFactsTrivia(
            question = "How many states are there?",
            answers = listOf("50", "35", "40", "45")
        )
    )

    lateinit var currentQuestion: EssentialFactsTrivia
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = min((triviaQuestions.size + 1) / 2, 2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<TriviaFragmentBinding>(
            inflater, R.layout.trivia_fragment, container, false)

        randomizeQuestions()

        binding.trivia = this
        binding.questionRadioGroup.clearCheck()

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId

            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++

                    if (questionIndex < numQuestions) {
                        currentQuestion = triviaQuestions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {

                        view.findNavController().navigate(R.id.action_triviaFragment_to_congratulationsFragment)
                    }
                } else {

                    view.findNavController().navigate(R.id.action_triviaFragment_to_keepPracticingFragment)
                }

                binding.questionRadioGroup.clearCheck()
                view.clearAnimation()
            }
        }

        return binding.root
    }

    private fun randomizeQuestions() {
        triviaQuestions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = triviaQuestions[questionIndex]

        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.trivia_questions_title, questionIndex + 1, numQuestions)
    }
}
