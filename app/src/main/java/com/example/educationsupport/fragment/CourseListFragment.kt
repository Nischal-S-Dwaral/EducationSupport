package com.example.educationsupport.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.CourseListCardAdapter

class CourseListFragment : Fragment() {

    private lateinit var courseListRecyclerView: RecyclerView
    private lateinit var coursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var coursesListAdaptor: CourseListCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_list, container, false)

        /**
         * Set up my courses recycler view
         */
        coursesListLayoutManager = LinearLayoutManager(view.context)
        coursesListAdaptor = CourseListCardAdapter(arrayOf(
            "Course 0", "Course 1", "Course 2", "Course 3", "Course 4", "Course 5"),
            view.context)
        courseListRecyclerView = view.findViewById(R.id.rv_courses_list_cards)
        courseListRecyclerView.setHasFixedSize(true)
        courseListRecyclerView.layoutManager = coursesListLayoutManager
        courseListRecyclerView.adapter = coursesListAdaptor

        return view
    }
}