package com.example.educationsupport.fragment.learner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.EnrolledCourseListCardAdapter
import com.example.educationsupport.model.EnrolledCourse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EnrolledCoursesFragment : Fragment() {

    private lateinit var view: View

    private lateinit var enrolledCourseListRecyclerView: RecyclerView
    private lateinit var enrolledCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var enrolledCoursesListAdaptor: EnrolledCourseListCardAdapter

    private lateinit var currentUser: FirebaseUser
    private lateinit var tvEnrolledCourses: TextView
    private lateinit var tvNoEnrolledCourses: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_enrolled_courses, container, false)

        /**
         * Initialise
         */
        currentUser = FirebaseAuth.getInstance().currentUser!!
        tvEnrolledCourses = view.findViewById(R.id.tv_enrolled_courses)
        tvNoEnrolledCourses = view.findViewById(R.id.tv_no_enrolled_courses)

        /**
         * Initialise the courses recycler view
         */
        enrolledCoursesListLayoutManager = LinearLayoutManager(view.context)
        enrolledCourseListRecyclerView = view.findViewById(R.id.rv_enrolled_courses_list_cards)
        enrolledCourseListRecyclerView.setHasFixedSize(true)
        enrolledCourseListRecyclerView.layoutManager = enrolledCoursesListLayoutManager

        /**
         * Get the enrolled courses list
         */
        getEnrolledCourseList()

        return view
    }

    private fun getEnrolledCourseList() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("EnrolledCourses")
        val enrolledCourseList : ArrayList<EnrolledCourse> = arrayListOf()

        databaseReference.orderByChild("learnerId").equalTo(currentUser.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (enrolledCourseSnap in snapshot.children) {
                            val enrolledCourseData = enrolledCourseSnap.getValue(EnrolledCourse::class.java)
                            enrolledCourseList.add(enrolledCourseData!!)
                        }
                        /**
                         * Set up the adapter for the recycler view
                         */
                        enrolledCoursesListAdaptor = EnrolledCourseListCardAdapter(enrolledCourseList, view.context)
                        enrolledCourseListRecyclerView.adapter = enrolledCoursesListAdaptor
                    } else {
                        tvNoEnrolledCourses.visibility = View.VISIBLE
                        tvEnrolledCourses.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(view.context, error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}