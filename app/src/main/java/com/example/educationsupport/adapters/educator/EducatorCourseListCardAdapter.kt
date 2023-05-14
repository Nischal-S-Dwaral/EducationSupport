package com.example.educationsupport.adapters.educator

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.educator.EducatorCourseActivity
import com.example.educationsupport.model.Course

class EducatorCourseListCardAdapter(private val coursesDataset: ArrayList<Course>, private val context: Context) :
    RecyclerView.Adapter<EducatorCourseListCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvEducatorCourseTitle: TextView
        var tvEducatorCourseDescription: TextView
        var btnMoreDetails : Button

        init {
            tvEducatorCourseTitle = itemView.findViewById(R.id.tv_educator_course_title)
            tvEducatorCourseDescription = itemView.findViewById(R.id.tv_educator_course_description)
            btnMoreDetails = itemView.findViewById(R.id.btn_educator_course_more_details)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.educator_course_list_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val course = coursesDataset[position]

        /**
         * Set the Course Name to the card
         */
        holder.tvEducatorCourseTitle.text = course.name

        /**
         * Set the course description to the card
         */
        holder.tvEducatorCourseDescription.text = buildString {
            append(course.description?.substring(0, course.description.length.coerceAtMost(100)) ?: "Test???")
            append("...")
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.btnMoreDetails.setOnClickListener {
            val intent = Intent(context, EducatorCourseActivity::class.java)
            intent.putExtra(Constants.COURSE_ID, course.id)
            intent.putExtra("courseName",course.name)
            intent.putExtra("courseDesc",course.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}