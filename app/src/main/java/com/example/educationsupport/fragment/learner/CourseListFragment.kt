package com.example.educationsupport.fragment.learner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CourseCardAdapter
import com.example.educationsupport.constants.CourseListConstants

class CourseListFragment : Fragment() {

    private lateinit var courseListRecyclerView: RecyclerView
    private lateinit var courseListLayoutManager: RecyclerView.LayoutManager
    private lateinit var courseListAdapter: CourseCardAdapter

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_list, container, false)

        /**
         * Get the available courses
         */
        val courseList = CourseListConstants.getCourseList()

        /**
         * Initialize the recycler view
         */
        courseListLayoutManager = LinearLayoutManager(view.context)
        courseListAdapter = CourseCardAdapter(courseList, view.context)
        courseListRecyclerView = view.findViewById(R.id.rv_courses_list_cards)
        courseListRecyclerView.setHasFixedSize(true)

        /**
         * Search view filtering
         */
        searchView = view.findViewById(R.id.search_view_learner_list)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                courseListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                courseListAdapter.filter.filter(newText)
                return false
            }

        })

        /**
         * Setup the courses recycler view
         */
        courseListRecyclerView.layoutManager = courseListLayoutManager
        courseListRecyclerView.adapter = courseListAdapter

        return view
    }
}