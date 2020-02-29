package org.ohmstheresistance.essentialfacts.recyclerview

import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.audio_files_itemview.view.*
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.data.AudioFilesInfo
import org.ohmstheresistance.essentialfacts.fragments.*


class AudioFilesAdapter(private val audioFilesList: ArrayList<AudioFilesInfo>) :
    RecyclerView.Adapter<AudioFilesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.audio_files_itemview, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(audioFilesList[position])

        val audioPosition = audioFilesList[position]

        if(audioFileIndex == holder.layoutPosition ){
            holder.itemView.audio_files_imageview.setBackgroundColor(Color.parseColor("#138b83"))
        }else{
            holder.itemView.audio_files_imageview.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        holder.itemView.setOnClickListener {

            mediaPlayer.release()
            handler.removeCallbacksAndMessages(null)

            audioFileIndex = position
            notifyDataSetChanged()
            println("MAX    " + audioFileIndex)

            mediaPlayer = MediaPlayer.create(holder.itemView.context, audioPosition.rawName)
            seekBar.progress = 0
            seekBar.max = mediaPlayer.duration
            audioNameTextView.text = audioPosition.name

            playPauseButton.performClick()

            notifyItemChanged(audioFileIndex)

            mediaPlayer.setOnCompletionListener {

                if (audioFileIndex < audioFilesList.size - 1) {

                    forwardButton.performClick()
                    forwardButton.isSoundEffectsEnabled = false
                }else {
                    audioFileIndex = 0

                    playPauseButton.performClick()
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return audioFilesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(audioFilesInfo: AudioFilesInfo) {

            itemView.audio_files_name_textview.text = audioFilesInfo.name
        }
    }
}