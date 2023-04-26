package com.example.educationsupport.fragment.educator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.constants.EducatorCourseConstants


class EducatorCoursesFragment : Fragment() {

    private lateinit var educatorCourseListRecyclerView: RecyclerView
    private lateinit var educatorCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var educatorCoursesListAdaptor: EducatorCourseListCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_educator_courses, container, false)

        /**
         * Get the educator course list
         */
        val educatorCourseList = EducatorCourseConstants.educatorCourseList();

        /**
         * Set up my courses recycler view
         */
        educatorCoursesListLayoutManager = LinearLayoutManager(view.context)
        educatorCoursesListAdaptor = EducatorCourseListCardAdapter(educatorCourseList, view.context)
        educatorCourseListRecyclerView = view.findViewById(R.id.rv_educator_courses_list_cards)
        educatorCourseListRecyclerView.setHasFixedSize(true)
        educatorCourseListRecyclerView.layoutManager = educatorCoursesListLayoutManager
        educatorCourseListRecyclerView.adapter = educatorCoursesListAdaptor

        return view
    }
}