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

    lateinit var audioList: ArrayList<Int>
    var elapsedTime: Long = 0
    var path: Int = 0
    var pathQuestion: String = ""

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

//        audioList = arrayListOf(R.raw.branches_of_government, R.raw.amount_of_senators, R.raw.delclaration_of_independence, R.raw.economic_system_of_us,
//            R.raw.first_ten_amendments_of_constitution, R.raw.first_three_words_of_constitution, R.raw.freedom_of_religion)
//        audioList.shuffle()
//        path = audioList[2]
//
//        mediaPlayer = MediaPlayer.create(context, path)
//        seekBar.max = mediaPlayer.duration
//
//        val nameOfAudio: String = resources.getResourceName(path)
//        audioNameTextView.text = resources.getResourceName(path).subSequence(41, nameOfAudio.length)

        val audioFiles = ArrayList<AudioFilesInfo>()
        audioFiles.add(AudioFilesInfo("What are the 3 branches of government?", R.raw.branches_of_government))
        audioFiles.add(AudioFilesInfo("How many senators are there?", R.raw.amount_of_senators))
        audioFiles.add(AudioFilesInfo("All Questions and Answers", R.raw.civicquestions))
        audioFiles.add(AudioFilesInfo("What do we call the first 10 amendments of the Constitution?", R.raw.first_ten_amendments_of_constitution))
        audioFiles.add(AudioFilesInfo("What are the first three words of The Constitution?", R.raw.first_three_words_of_constitution))
        audioFiles.add(AudioFilesInfo("How long are senators elected for?", R.raw.senator_term_length))
        audioFiles.add(AudioFilesInfo("What is the economic system of the U.S.?", R.raw.economic_system_of_us))
        audioFiles.add(AudioFilesInfo("What stops one branch of government from becoming too powerful?", R.raw.stopping_goverment_overreach))

        audioFiles.shuffle()

        path = audioFiles[4].rawName
        pathQuestion = audioFiles[4].name

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

            //mediaPlayer.seekTo(mediaPlayer.currentPosition - 6000)
            if (mediaPlayer != null) {
                mediaPlayer.reset()
                seekBar.progress = 0

                val newPath = path--
               // val newPathQuestion = pathQuestion

                mediaPlayer = MediaPlayer.create(context, newPath)
                seekBar.max = mediaPlayer.duration

                initializeSeekBar()

                val nameOfAudio: String = resources.getResourceName(newPath)
                audioNameTextView.text = resources.getResourceName(newPath).subSequence(41, nameOfAudio.length)

                playPauseButton.setImageResource(R.drawable.play_arrow)

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.pause()
                    playPauseButton.setImageResource(R.drawable.play_arrow)
                }
            }
        }

        forwardButton.setOnClickListener {

            if (mediaPlayer != null) {
                mediaPlayer.reset()
                seekBar.progress = 0

                val newPath = path++
                mediaPlayer = MediaPlayer.create(context, newPath)
                seekBar.max = mediaPlayer.duration

                initializeSeekBar()

                val nameOfAudio: String = resources.getResourceName(newPath)
                audioNameTextView.text =
                    resources.getResourceName(newPath).subSequence(41, nameOfAudio.length)

                playPauseButton.setImageResource(R.drawable.play_arrow)

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.pause()
                    playPauseButton.setImageResource(R.drawable.play_arrow)
                }
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
