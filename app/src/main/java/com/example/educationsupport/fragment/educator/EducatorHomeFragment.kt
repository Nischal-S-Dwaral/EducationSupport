package com.example.educationsupport.fragment.educator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.EnrolledCourseListCardAdapter
import com.example.educationsupport.adapters.learner.HomeCourseCardAdapter
import com.example.educationsupport.adapters.learner.MyActivitiesCardAdapter
import com.example.educationsupport.constants.EnrolledCoursesConstants

class EducatorHomeFragment : Fragment() {

    private lateinit var enrolledCourseListRecyclerView: RecyclerView
    private lateinit var enrolledCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var enrolledCoursesListAdaptor: EnrolledCourseListCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.educator_fragment_home, container, false)

        /**
         * Get the enrolled course list
         */
        val enrolledCourseList = EnrolledCoursesConstants.enrolledCourseList()

        /**
         * Set up my courses recycler view
         */
        enrolledCoursesListLayoutManager = LinearLayoutManager(view.context)
        enrolledCoursesListAdaptor = EnrolledCourseListCardAdapter(enrolledCourseList, view.context)
        enrolledCourseListRecyclerView = view.findViewById(R.id.educator_rv_enrolled_courses_list_cards)
        enrolledCourseListRecyclerView.setHasFixedSize(true)
        enrolledCourseListRecyclerView.layoutManager = enrolledCoursesListLayoutManager
        enrolledCourseListRecyclerView.adapter = enrolledCoursesListAdaptor

        return view
    }
}