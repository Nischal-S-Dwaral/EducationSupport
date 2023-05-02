package com.example.educationsupport.adapters.learner

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
import com.example.educationsupport.learner.ViewCourseActivity
import com.example.educationsupport.model.EnrolledCourse

class EnrolledCourseListCardAdapter(private val enrolledCoursesDataset: ArrayList<EnrolledCourse>, private val context: Context) :
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

        val enrolledCourse = enrolledCoursesDataset[position]

        /**
         * Set the Course Name to the card
         */
        holder.tvEnrolledCourseTitle.text = enrolledCourse.courseName

        /**
         * Set the course description to the card
         */
        holder.tvEnrolledCourseDescription.text = buildString {
            append(enrolledCourse.courseDescription?.substring(0, enrolledCourse.courseDescription.length.coerceAtMost(100))
                ?: "Default Course Description")
            append("...")
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.btnMoreDetails.setOnClickListener {
            val intent = Intent(context, ViewCourseActivity::class.java)
            intent.putExtra(Constants.COURSE_ID, enrolledCourse.courseId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return enrolledCoursesDataset.size
    }
}