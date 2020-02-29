package org.ohmstheresistance.essentialfacts.fragments

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.study_with_audio_fragment.*
import kotlinx.coroutines.Runnable
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.data.AudioFilesInfo
import org.ohmstheresistance.essentialfacts.databinding.StudyWithAudioFragmentBinding
import org.ohmstheresistance.essentialfacts.recyclerview.AudioFilesAdapter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


lateinit var mediaPlayer: MediaPlayer
lateinit var audioNameTextView: TextView
lateinit var seekBar: SeekBar
lateinit var playPauseButton: FloatingActionButton
lateinit var backButton: FloatingActionButton
lateinit var forwardButton: FloatingActionButton
var audioFileIndex = 0
lateinit var handler: Handler


class StudyWithAudioFragment : Fragment(){
    lateinit var binding: StudyWithAudioFragmentBinding
    lateinit var runnable: Runnable
    lateinit var currentTimeTextView: TextView
    lateinit var maxTimeTextView: TextView

    var path: Int = 0
    var elapsedTime: Long = 0
    var pathQuestion: String = ""
    lateinit var audioFilesList: ArrayList<AudioFilesInfo>

    lateinit var audioFilesAdapter: AudioFilesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.study_with_audio_fragment, container, false)

        setUp()
        initializeSeekBar()
        setUpMediaPlayer()

        return binding.root
    }
    private fun setUp(){

        val audioRecyclerView: RecyclerView = binding.audioRecyclerView
        handler = Handler()
        runnable = Runnable {}

        seekBar = binding.audioSeekbar
        audioNameTextView = binding.audioNameTextview
        currentTimeTextView = binding.audioCurrentTimeTextView
        maxTimeTextView = binding.audioMaxTimeTextView
        playPauseButton = binding.audioPlayPauseButton
        backButton = binding.audioBackButton
        forwardButton = binding.audioForwardButton

        audioFilesList = ArrayList()
        audioFilesList.add(AudioFilesInfo("Three branches of government", R.raw.branches_of_government))
        audioFilesList.add(AudioFilesInfo("Total senators", R.raw.amount_of_senators))
        audioFilesList.add(AudioFilesInfo("All questions and answers", R.raw.civicquestions))
        audioFilesList.add(AudioFilesInfo("First 10 amendments of the Constitution", R.raw.first_ten_amendments_of_constitution))
        audioFilesList.add(AudioFilesInfo("First three words of The Constitution", R.raw.first_three_words_of_constitution))
        audioFilesList.add(AudioFilesInfo("Senator term length", R.raw.senator_term_length))
        audioFilesList.add(AudioFilesInfo("Economic system of the U.S.", R.raw.economic_system_of_us))
        audioFilesList.add(AudioFilesInfo("Stopping over reach", R.raw.stopping_goverment_overreach))
        audioFilesList.add(AudioFilesInfo("Age to vote", R.raw.age_to_vote_for_president))
        audioFilesList.add(AudioFilesInfo("We pledge loyalty to", R.raw.allegiance_pledge_loyalty))
        audioFilesList.add(AudioFilesInfo("Reasons Benjamin Franklin is well known", R.raw.ben_franklin_famous_for))
        audioFilesList.add(AudioFilesInfo("Cabinet level positions", R.raw.cabinet_positions))
        audioFilesList.add(AudioFilesInfo("Brought to U.S. as slaves", R.raw.brought_to_america_as_slaves))
        audioFilesList.add(AudioFilesInfo("Cold War main concern", R.raw.cold_war_fears))
        audioFilesList.add(AudioFilesInfo("Commander in Chief of military", R.raw.commander_in_chief_of_military))
        audioFilesList.add(AudioFilesInfo("Constitutional Convention", R.raw.constitutional_convention))
        audioFilesList.add(AudioFilesInfo("When Declaration of Independence was adopted", R.raw.declaration_of_independence_adopted))
        audioFilesList.add(AudioFilesInfo("What did the Declaration of Independence do?", R.raw.declaration_of_independence))
        audioFilesList.add(AudioFilesInfo("Eisenhower served in which war?", R.raw.eisenhower_war))
        audioFilesList.add(AudioFilesInfo("What did the Emancipation Proclamation do?", R.raw.emancipation_proclamation))
        audioFilesList.add(AudioFilesInfo("Father of our country", R.raw.father_of_country))
        audioFilesList.add(AudioFilesInfo("Last day to submit tax form", R.raw.fed_tax_form_final_date))
        audioFilesList.add(AudioFilesInfo("Powers of Federal government", R.raw.federal_govt_powers))
        audioFilesList.add(AudioFilesInfo("Federalist Papers writers", R.raw.federalist_papers_writers))
        audioFilesList.add(AudioFilesInfo("First U.S. President", R.raw.first_president))
        audioFilesList.add(AudioFilesInfo("Why are there 50 stars on the flag", R.raw.flag_fifty_stars))
        audioFilesList.add(AudioFilesInfo("Why are there 13 stripes on the flag?", R.raw.flag_thirteen_stripes))
        audioFilesList.add(AudioFilesInfo("Freedom of religion", R.raw.freedom_of_religion))
        audioFilesList.add(AudioFilesInfo("First amendment freedoms", R.raw.freedoms_from_first_amendment))
        audioFilesList.add(AudioFilesInfo("Highest U.S. Court", R.raw.highest_us_court))
        audioFilesList.add(AudioFilesInfo("House Representative term length", R.raw.house_rep_term_length))
        audioFilesList.add(AudioFilesInfo("If President and Vice President can't serve", R.raw.if_pres_and_vp_cant_serve))
        audioFilesList.add(AudioFilesInfo("If the President can't serve", R.raw.if_pres_cant_serve))
        audioFilesList.add(AudioFilesInfo("Who lived in the U.S. before europeans came?", R.raw.in_america_before_europeans))
        audioFilesList.add(AudioFilesInfo("Lincoln's importance", R.raw.lincolns_importance))
        audioFilesList.add(AudioFilesInfo("What month do we elect presidents?", R.raw.month_of_pres_election))
        audioFilesList.add(AudioFilesInfo("Movement to end discrimination", R.raw.movement_to_end_discrimination))
        audioFilesList.add(AudioFilesInfo("Native American tribes", R.raw.native_american_tribes))
        audioFilesList.add(AudioFilesInfo("Ocean on U.S. east coast", R.raw.ocean_on_east_of_us))
        audioFilesList.add(AudioFilesInfo("Ocean on U.S. west coast", R.raw.ocean_on_west_of_us))
        audioFilesList.add(AudioFilesInfo("Thirteen original states", R.raw.original_states))
        audioFilesList.add(AudioFilesInfo("President during WWI", R.raw.pres_during_ww1))
        audioFilesList.add(AudioFilesInfo("President during WWII", R.raw.pres_during_ww2))
        audioFilesList.add(AudioFilesInfo("President term length", R.raw.president_term_length))
        audioFilesList.add(AudioFilesInfo("Problems that led to Civil War", R.raw.problems_leading_to_civil_war))
        audioFilesList.add(AudioFilesInfo("Promise when becoming a U.S. citizen", R.raw.promise_when_becoming_us_citizen))
        audioFilesList.add(AudioFilesInfo("Reasons colonists came to America", R.raw.reasons_colonists_came_to_america))
        audioFilesList.add(AudioFilesInfo("Rights in Declaration of Independence", R.raw.rights_in_declaration_of_independence))
        audioFilesList.add(AudioFilesInfo("Rights of everyone in the U.S.", R.raw.rights_of_everyone))
        audioFilesList.add(AudioFilesInfo("Role of President's cabinet", R.raw.role_of_cabinet))
        audioFilesList.add(AudioFilesInfo("Role of judicial branch of the government", R.raw.role_of_judicial_branch))
        audioFilesList.add(AudioFilesInfo("Age to sign up for Selective Service", R.raw.selective_service_age))
        audioFilesList.add(AudioFilesInfo("States bordering Canada", R.raw.states_bordering_canada))
        audioFilesList.add(AudioFilesInfo("States bordering Mexico", R.raw.states_bordering_mexico))
        audioFilesList.add(AudioFilesInfo("Some states have more representatives because", R.raw.states_have_more_reps_because))
        audioFilesList.add(AudioFilesInfo("Powers of the states", R.raw.states_powers))
        audioFilesList.add(AudioFilesInfo("Statue of Liberty location", R.raw.statue_of_liberty_location))
        audioFilesList.add(AudioFilesInfo("Supreme law of the land", R.raw.supreme_law_of_the_land))
        audioFilesList.add(AudioFilesInfo("Susan B. Anthony", R.raw.susan_b_anthony))
        audioFilesList.add(AudioFilesInfo("Territory bought from France in 1803", R.raw.territory_bought_from_france))
        audioFilesList.add(AudioFilesInfo("Total constitutional amendments", R.raw.total_amendments_to_constitution))
        audioFilesList.add(AudioFilesInfo("Total Representatives in House", R.raw.total_members_in_house))
        audioFilesList.add(AudioFilesInfo("Two parts of Congress", R.raw.two_parts_of_congress))
        audioFilesList.add(AudioFilesInfo("Two major political parties", R.raw.two_political_parties))
        audioFilesList.add(AudioFilesInfo("Capital of the U.S.", R.raw.us_capital))
        audioFilesList.add(AudioFilesInfo("Responsibilities of U.S. citizens", R.raw.us_citizen_responsibility))
        audioFilesList.add(AudioFilesInfo("Exclusive rights of citizens", R.raw.us_citizens_rights))
        audioFilesList.add(AudioFilesInfo("Longest rivers in U.S.", R.raw.us_longest_rivers))
        audioFilesList.add(AudioFilesInfo("U.S. national holidays", R.raw.us_national_holidays))
        audioFilesList.add(AudioFilesInfo("U.S territories", R.raw.us_territories))
        audioFilesList.add(AudioFilesInfo("U.S. wars in 1800s", R.raw.us_wars_in_1800s))
        audioFilesList.add(AudioFilesInfo("U.S. wars in 1900s", R.raw.us_wars_of_1900s))
        audioFilesList.add(AudioFilesInfo("War between North and South", R.raw.war_btw_north_and_south))
        audioFilesList.add(AudioFilesInfo("Ways to participate in democracy", R.raw.ways_to_participate_in_democracy))
        audioFilesList.add(AudioFilesInfo("What does the Constitution do?", R.raw.what_does_the_constitution_do))
        audioFilesList.add(AudioFilesInfo("Importance of 09/11/01", R.raw.what_happened_on_911))
        audioFilesList.add(AudioFilesInfo("What is an amendment?", R.raw.what_is_an_amendment))
        audioFilesList.add(AudioFilesInfo("What is the rule of law", R.raw.what_is_the_rule_of_law))
        audioFilesList.add(AudioFilesInfo("Martin Luther King, Jr", R.raw.what_mlk_did))
        audioFilesList.add(AudioFilesInfo("When is Independence Day celebrated?", R.raw.when_independence_day_celebrated))
        audioFilesList.add(AudioFilesInfo("When was the Constitution written?", R.raw.when_was_constitution_written))
        audioFilesList.add(AudioFilesInfo("Four voting amendments", R.raw.who_can_vote))
        audioFilesList.add(AudioFilesInfo("In charge of Executive Branch", R.raw.who_is_in_charge_of_executive_branch))
        audioFilesList.add(AudioFilesInfo("Who makes Federal Laws?", R.raw.who_makes_federal_law))
        audioFilesList.add(AudioFilesInfo("Who senators represent", R.raw.who_senators_represent))
        audioFilesList.add(AudioFilesInfo("Who signs bills into law?", R.raw.who_signs_bills))
        audioFilesList.add(AudioFilesInfo("Who vetoes bills?", R.raw.who_vetoes_bills))
        audioFilesList.add(AudioFilesInfo("Who wrote the Declaration of Independence?", R.raw.who_wrote_declaration_of_independence))
        audioFilesList.add(AudioFilesInfo("Why colonists fought the British", R.raw.why_colonists_fought_british))
        audioFilesList.add(AudioFilesInfo("WWII enemies", R.raw.ww2_enemies))

        audioFilesList.shuffle()

        audioFileIndex = 0

        path = audioFilesList[audioFileIndex].rawName
        pathQuestion = audioFilesList[audioFileIndex].name

        audioNameTextView.text = pathQuestion
        mediaPlayer = MediaPlayer.create(context, path)
        seekBar.max = mediaPlayer.duration

        audioFilesAdapter = AudioFilesAdapter(audioFilesList)
        audioRecyclerView.layoutManager =LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        audioRecyclerView.adapter = audioFilesAdapter
    }

    private fun setUpMediaPlayer() {

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.pause()
            playPauseButton.setImageResource(R.drawable.play_arrow)
        }

        val isPlaying: Boolean = mediaPlayer.isPlaying
        playPauseButton.setImageResource(if (isPlaying) R.drawable.pause_icon else R.drawable.play_arrow)

        playPauseButton.setOnClickListener {
            val isPlaying: Boolean = mediaPlayer.isPlaying

            getAudioFocus()

            audioFilesAdapter.notifyItemChanged(audioFileIndex)

            if (isPlaying) {

                playPauseButton.setImageResource(R.drawable.play_arrow)
                mediaPlayer.pause()
                updateSeekBar()

            } else {
                playPauseButton.setImageResource(R.drawable.pause_icon)
                mediaPlayer.start()

                updateSeekBar()
            }
        }

        backButton.setOnClickListener {

            audioFilesAdapter.notifyItemChanged(audioFileIndex)

            mediaPlayer.reset()
            seekBar.progress = 0

            if(audioFileIndex == 0){
                audioFileIndex = audioFilesList.size - 1
            }
            audioFileIndex--

            path = audioFilesList[audioFileIndex].rawName
            val newPathTitle = audioFilesList[audioFileIndex].name

            mediaPlayer = MediaPlayer.create(context, path)
            seekBar.max = mediaPlayer.duration
            audioNameTextView.text = newPathTitle

            playPauseButton.performClick()
            playPauseButton.isSoundEffectsEnabled = false

            initializeSeekBar()
            audio_recycler_view.smoothScrollToPosition(audioFileIndex)

            mediaPlayer.setOnCompletionListener {

                audioFilesAdapter.notifyItemChanged(audioFileIndex)
                forwardButton.performClick()
            }
        }

        forwardButton.setOnClickListener {

            audioFilesAdapter.notifyItemChanged(audioFileIndex)

            mediaPlayer.reset()
            seekBar.progress = 0

            println("Pressed at max before plus plus" + audioFileIndex)

            if(audioFileIndex == audioFilesList.size - 1){
                audioFileIndex = 0
            }
            audioFileIndex++

            println("Pressed at max" + audioFileIndex)

            path = audioFilesList[audioFileIndex].rawName
            val newPathTitle = audioFilesList[audioFileIndex].name

            mediaPlayer = MediaPlayer.create(context, path)
            seekBar.max = mediaPlayer.duration
            audioNameTextView.text = newPathTitle


            playPauseButton.performClick()
            audioFilesAdapter.notifyItemChanged(audioFileIndex)

            initializeSeekBar()
            audio_recycler_view.smoothScrollToPosition(audioFileIndex)

            mediaPlayer.setOnCompletionListener {

             audioFilesAdapter.notifyItemChanged(audioFileIndex)

                if (audioFileIndex < audioFilesList.size - 1) {

                    forwardButton.performClick()
                    forwardButton.isSoundEffectsEnabled = false

                }else {

                    audioFileIndex = 0
                    mediaPlayer.start()
                    audioFilesAdapter.notifyItemChanged(audioFileIndex)
                }
            }
        }
    }

    private fun getAudioFocus() {
        val audioManager: AudioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val audioFocusChangeListener = OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause()

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start()

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(R.drawable.play_arrow)
            }
        }

        val result: Int = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start()

            mediaPlayer.setOnCompletionListener {

                audioFilesAdapter.notifyItemChanged(audioFileIndex)
                forwardButton.performClick()
            }
        }
    }

    private fun initializeSeekBar() {

        val maxTime = mediaPlayer.duration

        val currentTimeInMins = TimeUnit.MINUTES.toMinutes(elapsedTime)
        val maxTimeInMins = TimeUnit.MINUTES.toMinutes(maxTime.toLong())

        val ctMinutes = (currentTimeInMins / 1000 / 60)
        val ctSeconds = (currentTimeInMins / 1000) % 60
        val currentTimeInMinutes =
            String.format(Locale.getDefault(), "%02d:%02d", ctMinutes, ctSeconds)

        val minutes = (maxTimeInMins / 1000 / 60)
        val seconds = (maxTimeInMins / 1000) % 60
        val maxTimeInMinutes = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        currentTimeTextView.text = currentTimeInMinutes
        maxTimeTextView.text = maxTimeInMinutes

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int, fromUser: Boolean
            ) {

                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

        })
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition

        elapsedTime = mediaPlayer.currentPosition.toLong()

        val currentPositionTimeInMins = TimeUnit.MINUTES.toMinutes(elapsedTime)
        val minutes = (currentPositionTimeInMins / 1000 / 60)
        val seconds = (currentPositionTimeInMins / 1000) % 60

        val currentPositionInMinutes =
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)


        val maxTime = mediaPlayer.duration
        val timeRemaining = maxTime - elapsedTime

        val maxTimeInMins = TimeUnit.MINUTES.toMinutes(timeRemaining)

        val maxTimeMinutes = (maxTimeInMins / 1000 / 60)
        val maxTimeSeconds = (maxTimeInMins / 1000) % 60
        val timeRemainingInMinutes =
            String.format(Locale.getDefault(), "%02d:%02d", maxTimeMinutes, maxTimeSeconds)

        if (mediaPlayer.isPlaying) {

            runnable = Runnable {
                run {

                    updateSeekBar()
                    currentTimeTextView.text = currentPositionInMinutes
                    maxTimeTextView.text = timeRemainingInMinutes
                }
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun stopAudio(){
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()

        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()

        stopAudio()
    }
}
