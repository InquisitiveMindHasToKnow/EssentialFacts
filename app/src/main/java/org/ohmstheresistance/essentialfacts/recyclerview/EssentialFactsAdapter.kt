package org.ohmstheresistance.essentialfacts.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.study_info_itemview.view.*
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.network.EssentialFactsInfo

class EssentialFactsAdapter(val studyInfoList: ArrayList<EssentialFactsInfo>) : RecyclerView.Adapter<EssentialFactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.study_info_itemview, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(studyInfoList[position])


    }

    override fun getItemCount(): Int {
        return studyInfoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(essentialFactsInfo: EssentialFactsInfo) {
            itemView.study_question_textview.text = essentialFactsInfo.question
            itemView.study_answers_textview.text = essentialFactsInfo.answers

        }
    }
}