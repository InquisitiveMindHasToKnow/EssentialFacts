package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

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
        ),
        EssentialFactsTrivia(
            question = "A U.S. Senator is elected once every:",
            answers = listOf("Six years", "Two years", "Four years", "Ten years")
        ),
        EssentialFactsTrivia(
            question = "Under the Constitution, which of these powers does NOT belong to the federal government?",
            answers = listOf("Ratify amendments to the Constitution", "Declare war", "Print money", "Make treaties")
        ),
        EssentialFactsTrivia(
            question = "Which of these is something Benjamin Franklin is known for?",
            answers = listOf("He was the nation's first postmaster general", "He was the nation's second president", "He discovered electicity", "He was the first to sign the Constitution")
        ),
        EssentialFactsTrivia(
            question = "How many amendments does the Constitution have?",
            answers = listOf("27", "16", "25", "34")
        ),
        EssentialFactsTrivia(
            question = "The House of Representatives have ____ voting members:",
            answers = listOf("435", "395", "525", "100")
        ),
        EssentialFactsTrivia(
            question = "The U.S. Senate has ___ voting members:",
            answers = listOf("100", "50", "75", "25")
        ),
        EssentialFactsTrivia(
            question = "The Federalist Papers supported the passage of the U.S. Constitution. Which of these men was not one of the authors?",
            answers = listOf("John Adams", "James Madison", "John Jay", "Alexander Hamilton")
        ),
        EssentialFactsTrivia(
            question = "When was the Constitution written?",
            answers = listOf("1787", "1772", "1492", "1812")
        ),
        EssentialFactsTrivia(
            question = "Who is in charge of the federal government?",
            answers = listOf("the President", "the Speaker of the House", "the Senate Majority Leader", "The Chief Justice of the SCOTUS")
        ),
        EssentialFactsTrivia(
            question = "What are the two parts of the Congress?",
            answers = listOf("The House of Representatives and the Senate", "The Supreme Court and Washington D.C.", "The FBI and the ATF", "None of these answers")
        ),
        EssentialFactsTrivia(
            question = "What do we call the first ten amendments of the Constitution?",
            answers = listOf("The Bill of Rights", "Freedom of Expression", "The Declaration of Independence", "The Rule of Law")
        ),
        EssentialFactsTrivia(
            question = "The idea of self-government is in the first three words of the Constitution.  What are these words?",
            answers = listOf("We The People", "We Are One", "E Pluribus Unum", "Freedom of Religion")
        )
    )

    lateinit var currentQuestion: EssentialFactsTrivia
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = min((triviaQuestions.size + 1) / 2, 10)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<TriviaFragmentBinding>(
            inflater, R.layout.trivia_fragment, container, false)

        randomizeQuestions()

        binding.trivia = this
        binding.questionRadioGroup.clearCheck()

        Glide.with(this)
            .asGif()
            .load(R.drawable.us_flag)
            .into(binding.questionImage)

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

                        view.findNavController().navigate(TriviaFragmentDirections.actionTriviaFragmentToCongratulationsFragment(questionIndex, numQuestions))
                    }
                } else {

                    view.findNavController().navigate(TriviaFragmentDirections.actionTriviaFragmentToKeepPracticingFragment(questionIndex, numQuestions))
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
