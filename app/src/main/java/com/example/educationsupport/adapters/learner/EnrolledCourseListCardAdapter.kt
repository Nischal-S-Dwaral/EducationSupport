package com.example.educationsupport.adapters.learner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.learner.QuizListActivity

class EnrolledCourseListCardAdapter(private val coursesDataset: Array<String>, private val context: Context) :
    RecyclerView.Adapter<EnrolledCourseListCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvEnrolledCourseTitle: TextView
        var llEnrolledCourseListCardCollapsingLayout: LinearLayout
        var llEnrolledActivitiesQuizLayout: LinearLayout
        var llEnrolledDownloadCourseContents: LinearLayout
        var buttonCollapse: ImageButton
        var collpased : Boolean = true

        init {
            tvEnrolledCourseTitle = itemView.findViewById(R.id.tv_enrolled_course_title)
            llEnrolledCourseListCardCollapsingLayout = itemView.findViewById(R.id.ll_enrolled_course_list_card_collapsing_layout)
            llEnrolledActivitiesQuizLayout = itemView.findViewById(R.id.ll_enrolled_activities_quiz)
            llEnrolledDownloadCourseContents = itemView.findViewById(R.id.ll_enrolled_download_course_contents)
            buttonCollapse = itemView.findViewById(R.id.btn_enrolled_course_list_card_collapse)
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
        /**
         * Set the Course Name to the card
         */
        holder.tvEnrolledCourseTitle.text = coursesDataset[position]

        /**
         * Handling the Download Contents linear layout
         */
        holder.llEnrolledDownloadCourseContents.setOnClickListener {
            Toast.makeText(context, "COMING SOON!!", Toast.LENGTH_SHORT).show()
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.llEnrolledActivitiesQuizLayout.setOnClickListener {
            val intent = Intent(context, QuizListActivity::class.java)
            intent.putExtra("courseTitle", coursesDataset[position])
            context.startActivity(intent)
        }

        /**
         * Handle the card collapsing event
         */
        holder.buttonCollapse.setOnClickListener {
            if (holder.collpased) {
                holder.collpased = false
                holder.llEnrolledCourseListCardCollapsingLayout.visibility = View.VISIBLE
                holder.buttonCollapse.setImageResource(R.drawable.ic_up_arrow)
            } else {
                holder.collpased = true
                holder.llEnrolledCourseListCardCollapsingLayout.visibility = View.GONE
                holder.buttonCollapse.setImageResource(R.drawable.ic_down_arrow)
            }
        }
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}