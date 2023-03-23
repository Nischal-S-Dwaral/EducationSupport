package com.example.educationsupport.adapters.learner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R

class CourseCardAdapter(private val coursesDataset: Array<String>, private val context: Context) :
    RecyclerView.Adapter<CourseCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var linearLayout: LinearLayout
        private var cardIcon: ImageView
        var cardName: TextView

        init {
            linearLayout = itemView.findViewById(R.id.ll_course_card_background)
            cardIcon = itemView.findViewById(R.id.ll_course_card_icon)
            cardName = itemView.findViewById(R.id.ll_course_card_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.home_course_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO: Need to handle OnClickListener
        holder.cardName.text = coursesDataset[position]
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}