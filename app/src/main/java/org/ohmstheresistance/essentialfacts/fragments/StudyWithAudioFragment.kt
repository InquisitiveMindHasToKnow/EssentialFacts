package org.ohmstheresistance.essentialfacts.fragments

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
import kotlinx.coroutines.Runnable
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.data.AudioFilesInfo
import org.ohmstheresistance.essentialfacts.databinding.StudyWithAudioFragmentBinding
import org.ohmstheresistance.essentialfacts.recyclerview.AudioFilesAdapter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class StudyWithAudioFragment : Fragment() {

    lateinit var binding: StudyWithAudioFragmentBinding
    lateinit var mediaPlayer: MediaPlayer
    lateinit var seekBar: SeekBar
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var currentTimeTextView: TextView
    lateinit var maxTimeTextView: TextView
    lateinit var audioNameTextView: TextView
    lateinit var playPauseButton: FloatingActionButton
    lateinit var backButton: FloatingActionButton
    lateinit var forwardButton: FloatingActionButton

    var elapsedTime: Long = 0
    var path: Int = 0
    var pathQuestion: String = ""
    var audioFileIndex = 0
    lateinit var audioFiles: ArrayList<AudioFilesInfo>

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

        audioFiles = ArrayList()
        audioFiles.add(AudioFilesInfo("Three branches of government", R.raw.branches_of_government))
        audioFiles.add(AudioFilesInfo("Total senators", R.raw.amount_of_senators))
        audioFiles.add(AudioFilesInfo("All questions and answers", R.raw.civicquestions))
        audioFiles.add(AudioFilesInfo("First 10 amendments of the Constitution", R.raw.first_ten_amendments_of_constitution))
        audioFiles.add(AudioFilesInfo("First three words of The Constitution", R.raw.first_three_words_of_constitution))
        audioFiles.add(AudioFilesInfo("Senator term length", R.raw.senator_term_length))
        audioFiles.add(AudioFilesInfo("Economic system of the U.S.", R.raw.economic_system_of_us))
        audioFiles.add(AudioFilesInfo("Stopping over reach", R.raw.stopping_goverment_overreach))
        audioFiles.add(AudioFilesInfo("Age to vote", R.raw.age_to_vote_for_president))
        audioFiles.add(AudioFilesInfo("We pledge loyalty to", R.raw.allegiance_pledge_loyalty))
        audioFiles.add(AudioFilesInfo("Reasons Benjamin Franklin is well known", R.raw.ben_franklin_famous_for))
        audioFiles.add(AudioFilesInfo("Cabinet level positions", R.raw.cabinet_positions))
        audioFiles.add(AudioFilesInfo("Brought to U.S. as slaves", R.raw.brought_to_america_as_slaves))
        audioFiles.add(AudioFilesInfo("Cold War main concern", R.raw.cold_war_fears))
        audioFiles.add(AudioFilesInfo("Commander in Chief of military", R.raw.commander_in_chief_of_military))
        audioFiles.add(AudioFilesInfo("Constitutional Convention", R.raw.constitutional_convention))
        audioFiles.add(AudioFilesInfo("When Declaration of Independence was adopted", R.raw.declaration_of_independence_adopted))
        audioFiles.add(AudioFilesInfo("What did the Declaration of Independence do?", R.raw.declaration_of_independence))
        audioFiles.add(AudioFilesInfo("Eisenhower served in which war?", R.raw.eisenhower_war))
        audioFiles.add(AudioFilesInfo("What did the Emancipation Proclamation do?", R.raw.emancipation_proclamation))
        audioFiles.add(AudioFilesInfo("Father of our country", R.raw.father_of_country))
        audioFiles.add(AudioFilesInfo("Last day to submit tax form", R.raw.fed_tax_form_final_date))
        audioFiles.add(AudioFilesInfo("Powers of Federal government", R.raw.federal_govt_powers))
        audioFiles.add(AudioFilesInfo("Federalist Papers writers", R.raw.federalist_papers_writers))
        audioFiles.add(AudioFilesInfo("First U.S. President", R.raw.first_president))
        audioFiles.add(AudioFilesInfo("Why are there 50 stars on the flag", R.raw.flag_fifty_stars))
        audioFiles.add(AudioFilesInfo("Why are there 13 stripes on the flag?", R.raw.flag_thirteen_stripes))
        audioFiles.add(AudioFilesInfo("Freedom of religion", R.raw.freedom_of_religion))
        audioFiles.add(AudioFilesInfo("First amendment freedoms", R.raw.freedoms_from_first_amendment))
        audioFiles.add(AudioFilesInfo("Highest U.S. Court", R.raw.highest_us_court))
        audioFiles.add(AudioFilesInfo("House Representative term length", R.raw.house_rep_term_length))
        audioFiles.add(AudioFilesInfo("Who takes over if President and Vice President can't serve?", R.raw.if_pres_and_vp_cant_serve))
        audioFiles.add(AudioFilesInfo("Who takes over if the President can't serve?", R.raw.if_pres_cant_serve))
        audioFiles.add(AudioFilesInfo("Who lived in the U.S. before europeans came?", R.raw.in_america_before_europeans))
        audioFiles.add(AudioFilesInfo("Lincoln's importance", R.raw.lincolns_importance))
        audioFiles.add(AudioFilesInfo("What month do we elect presidents?", R.raw.month_of_pres_election))


        audioFileIndex = audioFiles.size - 1

        path = audioFiles[audioFileIndex].rawName
        pathQuestion = audioFiles[audioFileIndex].name

        audioNameTextView.text = pathQuestion
        mediaPlayer = MediaPlayer.create(context, path)
        seekBar.max = mediaPlayer.duration

        audioFilesAdapter = AudioFilesAdapter(audioFiles)
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
            mediaPlayer.start()

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
            forwardButton.isEnabled = true

            if (mediaPlayer != null) {
                mediaPlayer.reset()
                seekBar.progress = 0

                val newPath = audioFiles[audioFileIndex--].rawName
                val newPathTitle = audioFiles[audioFileIndex--].name

                println("back" + newPath)
                println("back"+ newPathTitle)

                mediaPlayer = MediaPlayer.create(context, newPath)
                seekBar.max = mediaPlayer.duration

                initializeSeekBar()

                audioNameTextView.text = newPathTitle

                playPauseButton.setImageResource(R.drawable.play_arrow)

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.pause()
                    playPauseButton.setImageResource(R.drawable.play_arrow)
                }
            }
            if(audioFileIndex == 0){
                backButton.isEnabled = false
            }
        }

        forwardButton.setOnClickListener {
            backButton.isEnabled = true

            if (mediaPlayer != null) {
                mediaPlayer.reset()
                seekBar.progress = 0

                val newPath = audioFiles[audioFileIndex++].rawName
                val newPathTitle = audioFiles[audioFileIndex++].name

                println("forward" + newPath)
                println("forward"+ newPathTitle)

                mediaPlayer = MediaPlayer.create(context, newPath)
                seekBar.max = mediaPlayer.duration

                initializeSeekBar()

                audioNameTextView.text = newPathTitle

                playPauseButton.setImageResource(R.drawable.play_arrow)

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.pause()
                    playPauseButton.setImageResource(R.drawable.play_arrow)
                }
            }
            if(audioFileIndex == audioFiles.size - 1){
                forwardButton.isEnabled = false
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
