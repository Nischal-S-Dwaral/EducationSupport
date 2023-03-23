package com.example.educationsupport.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R

class CourseListCardAdapter(private val coursesDataset: Array<String>, private val context: Context) :
    RecyclerView.Adapter<CourseListCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCourseTitle: TextView
        var llCourseListCardCollapsingLayout: LinearLayout
        var llActivitiesTests: LinearLayout
        var buttonCollapse: ImageButton
        var collpased : Boolean = true

        init {
            tvCourseTitle = itemView.findViewById(R.id.tv_course_title)
            llCourseListCardCollapsingLayout = itemView.findViewById(R.id.ll_course_list_card_collapsing_layout)
            llActivitiesTests = itemView.findViewById(R.id.ll_activities_tests)
            buttonCollapse = itemView.findViewById(R.id.btn_course_list_card_collapse)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.course_list_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO: Need to handle OnClickListener
        /**
         * Set the Course Name to the card
         */
        holder.tvCourseTitle.text = coursesDataset[position]

        /**
         * Handle the card collapsing event
         */
        holder.buttonCollapse.setOnClickListener {
            if (holder.collpased) {
                holder.collpased = false
                holder.llCourseListCardCollapsingLayout.visibility = View.VISIBLE
                holder.buttonCollapse.setImageResource(R.drawable.ic_up_arrow)
            } else {
                holder.collpased = true
                holder.llCourseListCardCollapsingLayout.visibility = View.GONE
                holder.buttonCollapse.setImageResource(R.drawable.ic_down_arrow)
            }
        }
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}