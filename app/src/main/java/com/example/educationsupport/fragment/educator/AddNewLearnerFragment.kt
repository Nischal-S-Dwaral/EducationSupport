package com.example.educationsupport.fragment.educator

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
import com.example.educationsupport.adapters.learner.LearnerSearchAdapter
import com.example.educationsupport.constants.CourseListConstants
import com.example.educationsupport.constants.LearnerListConstants

class AddNewLearnerFragment : Fragment() {

    private lateinit var learnerListRecyclerView: RecyclerView
    private lateinit var learnerListLayoutManager: RecyclerView.LayoutManager
    private lateinit var LearnerSearchAdapter: LearnerSearchAdapter
    private lateinit var searchView: SearchView
//    private lateinit var courseListAdapter: CourseCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val learnerList = LearnerListConstants.getLearnerList()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.educator_add_learner, container, false)
//        courseListAdapter = CourseCardAdapter(courseList, view.context)
        LearnerSearchAdapter = LearnerSearchAdapter(learnerList, view.context)
        learnerListLayoutManager = LinearLayoutManager(view.context)
//        learnerListRecyclerView = view.findViewById(R.id.rv_learner_list_cards)
//        learnerListRecyclerView.setHasFixedSize(true)
        /**
         * Search view filtering
         */
        searchView = view.findViewById(R.id.search_view_learner_list)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                LearnerSearchAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LearnerSearchAdapter.filter.filter(newText)
                return false
            }

        })

//        learnerListRecyclerView.layoutManager = learnerListLayoutManager
//        learnerListRecyclerView.adapter = LearnerSearchAdapter

        return view
    }
}