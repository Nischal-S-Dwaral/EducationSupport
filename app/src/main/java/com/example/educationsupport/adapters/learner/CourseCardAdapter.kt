package com.example.educationsupport.adapters.learner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.learner.ViewCourseActivity
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.CourseSearch

class CourseCardAdapter(private val courseList: ArrayList<Course>, private val context: Context) :
    RecyclerView.Adapter<CourseCardAdapter.ViewHolder>(), Filterable {

    var courseFilterList = ArrayList<Course>()

    init {
        courseFilterList = courseList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayoutCard: LinearLayout
        var tvCourseTitle: TextView
        var tvEducatorName: TextView

        init {
            linearLayoutCard = itemView.findViewById(R.id.ll_course_list_card)
            tvCourseTitle = itemView.findViewById(R.id.tv_course_list_course_title)
            tvEducatorName = itemView.findViewById(R.id.tv_course_list_educator_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.course_list_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val course = courseFilterList[position]

        /**
         * Set the course name and educator name
         */
        holder.tvCourseTitle.text = course.name
        holder.tvEducatorName.text = buildString {
            append("By: ")
            append(course.educatorId)
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.linearLayoutCard.setOnClickListener {
            val intent = Intent(context, ViewCourseActivity::class.java)
            intent.putExtra(Constants.COURSE_ID, course.id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courseFilterList.size
    }

    /**
     * Filter based on the searched
     * Searching based on the course name
     */
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint?.toString()?.lowercase() ?: ""
                if (charSearch.isEmpty()) {
                    courseFilterList = courseList
                } else {
                    val filteredList = ArrayList<Course>()
                    courseList
                        .filter {
                            (it.name!!.lowercase().contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }

                    courseFilterList = filteredList
                }
                return FilterResults().apply { values = courseFilterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                courseFilterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Course>
                notifyDataSetChanged()
            }
        }
    }
}