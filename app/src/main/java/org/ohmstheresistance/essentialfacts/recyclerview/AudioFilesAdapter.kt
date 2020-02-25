package org.ohmstheresistance.essentialfacts.recyclerview

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.audio_files_itemview.view.*
import org.ohmstheresistance.essentialfacts.R

class AudioFilesAdapter(private val audioFilesList: ArrayList<Int>) : RecyclerView.Adapter<AudioFilesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.audio_files_itemview, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(audioFilesList[position])
    }

    override fun getItemCount(): Int {
        return audioFilesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(audioFilesInfo: Int) {

           val audioName = audioFilesInfo.toString()
            itemView.audio_files_name_textview.text = audioName
        }
    }
}