package com.example.educationsupport.fragment.learner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.EnrolledCourseListCardAdapter

class EnrolledCoursesFragment : Fragment() {

    private lateinit var enrolledCourseListRecyclerView: RecyclerView
    private lateinit var enrolledCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var enrolledCoursesListAdaptor: EnrolledCourseListCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enrolled_courses, container, false)

        /**
         * Set up my courses recycler view
         */
        enrolledCoursesListLayoutManager = LinearLayoutManager(view.context)
        enrolledCoursesListAdaptor = EnrolledCourseListCardAdapter(arrayOf(
            "Course 0", "Course 1", "Course 2", "Course 3", "Course 4", "Course 5"),
            view.context)
        enrolledCourseListRecyclerView = view.findViewById(R.id.rv_enrolled_courses_list_cards)
        enrolledCourseListRecyclerView.setHasFixedSize(true)
        enrolledCourseListRecyclerView.layoutManager = enrolledCoursesListLayoutManager
        enrolledCourseListRecyclerView.adapter = enrolledCoursesListAdaptor

        return view
    }
}