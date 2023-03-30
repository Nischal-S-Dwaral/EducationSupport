package com.example.educationsupport.adapters.learner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.learner.ViewCourseActivity
import com.example.educationsupport.model.Course

class EnrolledCourseListCardAdapter(private val coursesDataset: ArrayList<Course>, private val context: Context) :
    RecyclerView.Adapter<EnrolledCourseListCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvEnrolledCourseTitle: TextView
        var tvEnrolledCourseDescription: TextView
        var btnMoreDetails : Button

        init {
            tvEnrolledCourseTitle = itemView.findViewById(R.id.tv_enrolled_course_title)
            tvEnrolledCourseDescription = itemView.findViewById(R.id.tv_enrolled_course_description)
            btnMoreDetails = itemView.findViewById(R.id.btn_enrolled_course_more_details)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.enrolled_course_list_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val course = coursesDataset[position]

        /**
         * Set the Course Name to the card
         */
        holder.tvEnrolledCourseTitle.text = course.name

        /**
         * Set the course description to the card
         */
        holder.tvEnrolledCourseDescription.text = buildString {
            append(course.description.substring(0, course.description.length.coerceAtMost(100)))
            append("...")
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.btnMoreDetails.setOnClickListener {
            val intent = Intent(context, ViewCourseActivity::class.java)
            //TODO: Update this
            intent.putExtra(Constants.COURSE_ID, course.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}