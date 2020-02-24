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
import kotlinx.coroutines.Runnable
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.StudyWithAudioFragmentBinding
import java.util.*
import java.util.concurrent.TimeUnit


class StudyWithAudioFragment : Fragment() {

    lateinit var mediaPlayer: MediaPlayer
    lateinit var seekBar: SeekBar
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var currentTimeTextView: TextView
    lateinit var maxTimeTextView: TextView
    var currentPosition: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<StudyWithAudioFragmentBinding>(
            inflater,
            R.layout.study_with_audio_fragment,
            container,
            false
        )

        handler = Handler()
        runnable = Runnable {}
        mediaPlayer = MediaPlayer.create(context, R.raw.civicquestions)

        seekBar = binding.audioSeekbar
        seekBar.max = mediaPlayer.duration

        currentTimeTextView = binding.audioCurrentTimeTextView
        maxTimeTextView = binding.audioMaxTimeTextView

        initializeSeekBar()

        val isPlaying: Boolean = mediaPlayer.isPlaying
        binding.audioPlayPauseButton.setImageResource(if (isPlaying) R.drawable.pause_icon else R.drawable.play_arrow)

        binding.audioPlayPauseButton.setOnClickListener {
            val isPlaying: Boolean = mediaPlayer.isPlaying

            mediaPlayer.start()

            if (isPlaying) {

                binding.audioPlayPauseButton.setImageResource(R.drawable.play_arrow)
                mediaPlayer.pause()
                updateSeekBar()

            } else {
                binding.audioPlayPauseButton.setImageResource(R.drawable.pause_icon)
                mediaPlayer.start()

                updateSeekBar()
            }
        }

        binding.audioBackButton.setOnClickListener {

        }

        binding.audioForwardButton.setOnClickListener {

        }

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.pause()
        }


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
        return binding.root
    }

    private fun initializeSeekBar() {

        val maxTime = mediaPlayer.duration

        val currentTimeInMins = TimeUnit.MINUTES.toMinutes(currentPosition)
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
    }

    private fun updateSeekBar(){
        seekBar.progress = mediaPlayer.currentPosition

        currentPosition = mediaPlayer.currentPosition.toLong()

        val currentPositionTimeInMins = TimeUnit.MINUTES.toMinutes(currentPosition)
        val minutes = (currentPositionTimeInMins / 1000 / 60)
        val seconds = (currentPositionTimeInMins / 1000) % 60

        val currentPositionInMinutes =
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        if(mediaPlayer.isPlaying){

            runnable = Runnable {
                run{

                    updateSeekBar()
                    currentTimeTextView.text = currentPositionInMinutes
                }
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()

        handler.removeCallbacksAndMessages(null)

    }
}
