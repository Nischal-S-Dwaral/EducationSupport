package com.example.educationsupport.adapters.educator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.EnrolledCourse
import com.example.educationsupport.model.Users
import com.google.firebase.database.FirebaseDatabase

class LearnerListCardAdapter(
    private val LearnerDataset: ArrayList<Users>,
    private val context: Context,
    private val course: Course
) :
    RecyclerView.Adapter<LearnerListCardAdapter.ViewHolder?>(), Filterable {

    var learnerFilterList = ArrayList<Users>()

    init {
        learnerFilterList = LearnerDataset
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayoutCard: LinearLayout
        var tvLearnerEmail: TextView
        var tvLearnerId: TextView

        init {
            linearLayoutCard = itemView.findViewById(R.id.ll_learner_list_card)
            tvLearnerEmail = itemView.findViewById(R.id.tv_learner_list_course_title)
            tvLearnerId = itemView.findViewById(R.id.tv_learner_list_educator_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.learner_list_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val enrolledCourseDatabaseReference = FirebaseDatabase.getInstance().getReference("EnrolledCourses")

        val learner = learnerFilterList[position]

        /**
         * Set the course name and educator name
         */
        holder.tvLearnerEmail.text = learner.email
        holder.tvLearnerId.text = buildString {
            append("By: ")
            append(learner.uid)
        }

        /**
         * Handling onClickListener for the take test layout
         */
        holder.linearLayoutCard.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to add this learner to your course?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialog, id ->

                    val enrolledCourseId = enrolledCourseDatabaseReference.push().key!!
                    val enrolledCourse = EnrolledCourse(
                        enrolledCourseId,
                        course.name,
                        course.description,
                        course.id,
                        learner.uid
                    )

                    enrolledCourseDatabaseReference.child(enrolledCourseId).setValue(enrolledCourse)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Enrolled Learner Successfully!!", Toast.LENGTH_SHORT)
                                .show()

                        }.addOnFailureListener {
                            Toast.makeText(context, "Error!! ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }

                    val recipientEmail = learner.email
                    val subject = "Invitation to join " + course.name
                    val message = "I have enrolled you to the ${course.name}. Please have a look in the application."

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/html"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                        putExtra(Intent.EXTRA_SUBJECT, subject)
                        putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message))
                    }

                    context.startActivity(Intent.createChooser(intent, "Send email..."))
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return learnerFilterList.size
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
                    learnerFilterList = LearnerDataset
                } else {
                    val filteredList = ArrayList<Users>()
                    LearnerDataset
                        .filter {
                            (it.email!!.lowercase().contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }

                    learnerFilterList = filteredList
                }
                return FilterResults().apply { values = learnerFilterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                learnerFilterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Users>
                notifyDataSetChanged()
            }
        }
    }
}