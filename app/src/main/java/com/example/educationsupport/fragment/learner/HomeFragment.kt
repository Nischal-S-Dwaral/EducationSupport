package com.example.educationsupport.fragment.learner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CourseCardAdapter
import com.example.educationsupport.adapters.learner.MyActivitiesCardAdapter

class HomeFragment : Fragment() {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var coursesLayoutManager: RecyclerView.LayoutManager
    private lateinit var coursesAdaptor: CourseCardAdapter

    private lateinit var myActivitiesRecyclerView: RecyclerView
    private lateinit var myActivitiesLayoutManager: RecyclerView.LayoutManager
    private lateinit var myActivitiesAdaptor: MyActivitiesCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        /**
         * Set up my courses recycler view
         */
        coursesLayoutManager = GridLayoutManager(view.context, 2)
        coursesAdaptor = CourseCardAdapter(
            arrayOf(
                "Course 0", "Course 1", "Course 2", "Course 3", "Course 4", "Course 5"
            ),
            view.context
        )
        coursesRecyclerView = view.findViewById(R.id.rv_course_cards)
        coursesRecyclerView.setHasFixedSize(true)
        coursesRecyclerView.layoutManager = coursesLayoutManager
        coursesRecyclerView.adapter = coursesAdaptor

        /**
         * Set up my activities recycler view
         */
        myActivitiesLayoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        myActivitiesAdaptor = MyActivitiesCardAdapter(
            arrayOf(
                "Activity 0", "Activity 1", "Activity 2", "Activity 3", "Activity 4", "Activity 5"
            ),
            view.context
        )
        myActivitiesRecyclerView = view.findViewById(R.id.rv_activities_cards)
        myActivitiesRecyclerView.setHasFixedSize(true)
        myActivitiesRecyclerView.layoutManager = myActivitiesLayoutManager
        myActivitiesRecyclerView.adapter = myActivitiesAdaptor

        return view
    }
}