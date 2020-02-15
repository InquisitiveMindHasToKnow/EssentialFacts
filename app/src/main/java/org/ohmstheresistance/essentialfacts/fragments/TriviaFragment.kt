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
            question = "A U.S. Senator is elected for:",
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
        ),
        EssentialFactsTrivia(
            question = "What is the capital of New York?",
            answers = listOf("Albany", "Kingston", "Yonkers", "Manhattan")
        ),
        EssentialFactsTrivia(
            question = "If the President can no longer serve, who takes over?",
            answers = listOf("The Vice President", "The Speaker of the House", "The top military generals", "The Senate Minority Leader")
        ),
        EssentialFactsTrivia(
            question = "Who signs bills into laws?",
            answers = listOf("the President", "the cabinet", "the Supreme Court", "the Vice President")
        ),
        EssentialFactsTrivia(
            question = "Who vetoes bills?",
            answers = listOf("the President", "the Chief Justice of the SCOTUS", "the House of Representatives", "the FBI director")
        ),
        EssentialFactsTrivia(
            question = "What is the law of the land?",
            answers = listOf("the Constitution", "the Bible", "the Torah", "None of these")
        ),
        EssentialFactsTrivia(
            question = "The Constitution does each of these things except:",
            answers = listOf("Appoints judges", "Sets up the government", "Defines the government", "Protects basic rights of Americans")
        ),
        EssentialFactsTrivia(
            question = "What is one right or freedom from the First Amendment?",
            answers = listOf("All of these", "Freedom of speech", "Freedom of assembly", "Freedom of religion")
        ),
        EssentialFactsTrivia(
            question = "What country did the Declaration of Independence declare us independent from?",
            answers = listOf("Great Britain", "France", "Canada", "Germany")
        ),
        EssentialFactsTrivia(
            question = "What are two rights of the Declaration of Independence?",
            answers = listOf("life and liberty", "wealth and freedom", "pursuit of happiness and vast wealth", "liberty and property")
        ),
        EssentialFactsTrivia(
            question = "The three branches of government are?",
            answers = listOf("legislative, judicial and executive", "the Cabinet, the Senate, House of Representatives", "the Courts, the FBI, the State", "We The People, the Courts, Lawyers")
        ),
        EssentialFactsTrivia(
            question = "What stops one branch of government from becoming too powerful?",
            answers = listOf("Checks and Balances/ Separation of Powers", "Daily protests", "the Law", "the Statue of Liberty")
        ),
        EssentialFactsTrivia(
            question = "Who makes federal law?",
            answers = listOf("The Congress", "The President", "state Governors", "City Mayors")
        ),
        EssentialFactsTrivia(
            question = "Who do Senators represent?",
            answers = listOf("Everyone in their state", "Major corporation", "The US Congress", "The President")
        ),
        EssentialFactsTrivia(
            question = "Why do some states have more Representatives than others?",
            answers = listOf("They have more people", "They have more money", "Bribery", "They asked for them")
        ),
        EssentialFactsTrivia(
            question = "A President is elected for?",
            answers = listOf("Four years", "Six years", "Two years", "Nine years")
        ),
        EssentialFactsTrivia(
            question = "If the President and Vice President can no longer serve, who takes over?",
            answers = listOf("The Speaker of the House", "The Senate Leader", "Secretary of Taking Over ", "Police Commissioner")
        ),
        EssentialFactsTrivia(
            question = "The Commander in Chief of the military is?",
            answers = listOf("the President", "the top military general", "the Congress", "Secretary of Defense")
        ),
        EssentialFactsTrivia(
            question = "What are two cabinat level positions?",
            answers = listOf("Secretary of State/ Secretary of Defense", "Secretary of Education/Secretary of the FBI", "Secretary of the CIA/Secretary of Agriculture", "Secretary of the Treasury/Secretary of Airports")
        ),
        EssentialFactsTrivia(
            question = "The job of the Judicial Branch of government is to:",
            answers = listOf("Review Laws", "Make Laws", "Sign Laws", "None of these")
        ),
        EssentialFactsTrivia(
            question = "What is the highest court in the United States?",
            answers = listOf("The Supreme Court of the US", "State Court", "Federal Court", "Traffic Court")
        ),
        EssentialFactsTrivia(
            question = "Under our Constitution, some powers belong to the federal government.  What is one power of the federal government?",
            answers = listOf("all of these", "to print money", "to declare war", "to create an army")
        ),
        EssentialFactsTrivia(
            question = "Under our Constitution, some powers belong to the states.  What is one power of the states? ",
            answers = listOf("all of these", "provide schooling and education", "provide protection (police)", "give a driver’s license")
        ),
        EssentialFactsTrivia(
            question = "Who is the Governor of New York?",
            answers = listOf("Andrew Cuomo", "Michael Bloomberg", "Charles Schumer", "Jerry Nadler")
        ),
        EssentialFactsTrivia(
            question = "What is one responsibility of United States citizens?",
            answers = listOf("Serve on a jury", "Travel frequently", "Become millionaires", "Nothing")
        ),
        EssentialFactsTrivia(
            question = "What is one right of United States citizens?",
            answers = listOf("Ability to run for federal office", "Make money", "Buy a car", "Make laws")
        ),
        EssentialFactsTrivia(
            question = "When we say the Pledge of Allegiance we show loyalty to?",
            answers = listOf("The United States", "Presidents", "The Government", "Nothing")
        ),
        EssentialFactsTrivia(
            question = "What is one promise you make when you become a United States citizen?",
            answers = listOf("all of these", "give up loyalty to other countries", "obey the laws of the United States", "serve the nation(if needed)")
        ),
        EssentialFactsTrivia(
            question = "How old do citizens have to be to vote?",
            answers = listOf("18", "21", "14", "30")
        ),
        EssentialFactsTrivia(
            question = "What is a way for an American to participate in their democracy?",
            answers = listOf("all of these", "vote", "run for office", "publicly support or oppose an issue or policy")
        ),
        EssentialFactsTrivia(
            question = "When is the last day you can send in federal income tax forms?",
            answers = listOf("April 15", "February 28", "March 12", "January 31")
        ),
        EssentialFactsTrivia(
            question = "All men must register for the Selective Service at what age?",
            answers = listOf("18", "21", "15", "13")
        ),
        EssentialFactsTrivia(
            question = "Who lived in America before the Europeans arrived?",
            answers = listOf("Native Americans", "Canadians", "French", "Middle Easterners")
        ),
        EssentialFactsTrivia(
            question = "What group of people was taken to America and sold as slaves?",
            answers = listOf("Africans", "Spaniards", "Chinese", "Germans")
        ),
        EssentialFactsTrivia(
            question = "Who wrote the Declaration of Independence?",
            answers = listOf("Thomas Jeffereson", "James Madison", "Benjamin Franklin", "John Jay")
        ),
        EssentialFactsTrivia(
            question = "When was the Declaration of Independence adpoted?",
            answers = listOf("July 4, 1776", "July 4, 1900", "July 4, 1796", "July 4, 1812")
        ),
        EssentialFactsTrivia(
            question = "Three of the original 13 states are ____, ____ and ____",
            answers = listOf("New York, Connecticut and Pennsylvania", "South Dakota, Arizona, Maine ", "Florida, Texas, California", "New MExico, Kansas, Montana")
        ),
        EssentialFactsTrivia(
            question = "What happened at the Constitutional Convention?",
            answers = listOf("The Constitution was written", "The Constitution was ordered", "The Constitution was thought of", "There was no Constitution Convention")
        ),
        EssentialFactsTrivia(
            question = "Who was the first President?",
            answers = listOf("George Washington", "John F. Kennedy", "George Bush", "Jimmy Carter")
        ),

        EssentialFactsTrivia(
            question = "Who is the 'Father of Our Country'?",
            answers = listOf("George Washington", "James Madison", "King Henry VIII", "Andrew Jackson")
        ),
        EssentialFactsTrivia(
            question = "What territory did the United States buy from France in 1803?",
            answers = listOf("Louisiana", "North Dakota", "Maine", "Kentucky")
        ),
        EssentialFactsTrivia(
            question = "Name one war fought by the United States in the 1800s?",
            answers = listOf("War of 1812", "Iraq War", "World War II", "World War I")
        ),
        EssentialFactsTrivia(
            question = "The U.S. war between the North and the South was?",
            answers = listOf("the Civil War", "World War I", "the Spanish-American war ", "the Mexican-American war")
        ),
        EssentialFactsTrivia(
            question = "All of these problems led to the Civil War except?",
            answers = listOf("too much money", "slavery", "economic reasons", "states rights")
        ),
        EssentialFactsTrivia(
            question = "What did the Emancipation Proclamation do?",
            answers = listOf("freed the slaves", "made people slaves", "freed up money for people", "freed up land around the country")
        ),
        EssentialFactsTrivia(
            question = "What did Susan B. Anthony do?",
            answers = listOf("fought for civil/women's rights", "made electricity readily available", "ran the underground railroad", "created the stop light")
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
