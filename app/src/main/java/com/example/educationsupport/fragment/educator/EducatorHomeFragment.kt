package com.example.educationsupport.fragment.educator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.EnrolledCourseListCardAdapter
import com.example.educationsupport.constants.EnrolledCoursesConstants

class EducatorHomeFragment : Fragment() {

    private lateinit var myCourseListRecyclerView: RecyclerView
    private lateinit var myCoursesListLayoutManager: RecyclerView.LayoutManager
    //TODO: Different Adapter the Educator Course Details
    private lateinit var myCoursesListAdaptor: EnrolledCourseListCardAdapter

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
        myCoursesListLayoutManager = LinearLayoutManager(view.context)
        myCoursesListAdaptor = EnrolledCourseListCardAdapter(enrolledCourseList, view.context)
        myCourseListRecyclerView = view.findViewById(R.id.educator_rv_enrolled_courses_list_cards)
        myCourseListRecyclerView.setHasFixedSize(true)
        myCourseListRecyclerView.layoutManager = myCoursesListLayoutManager
        myCourseListRecyclerView.adapter = myCoursesListAdaptor

        return view
    }
}