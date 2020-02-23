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
    lateinit var runnable:Runnable
    lateinit var currentTimeTextView: TextView
    lateinit var maxTimeTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<StudyWithAudioFragmentBinding>(inflater, R.layout.study_with_audio_fragment, container, false)

        handler = Handler()
        mediaPlayer = MediaPlayer.create(context, R.raw.civicquestions)

        seekBar = binding.audioSeekbar
        currentTimeTextView = binding.audioCurrentTimeTextView
        maxTimeTextView = binding.audioMaxTimeTextView



        val isPlaying: Boolean = mediaPlayer.isPlaying
        binding.audioPlayPauseButton.setImageResource(if (isPlaying) R.drawable.pause_icon else R.drawable.play_arrow)

        binding.audioPlayPauseButton.setOnClickListener {
            val isPlaying: Boolean = mediaPlayer.isPlaying

            if(isPlaying){
                binding.audioPlayPauseButton.setImageResource(R.drawable.play_arrow)
                mediaPlayer.pause()

            }else {
                binding.audioPlayPauseButton.setImageResource(R.drawable.pause_icon)
                mediaPlayer.start()
            }
        }

        binding.audioBackButton.setOnClickListener {

        }

        binding.audioForwardButton.setOnClickListener {


        }

        return binding.root
    }
}
