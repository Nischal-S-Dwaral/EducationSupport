package com.example.educationsupport.fragment.learner

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.example.educationsupport.model.Course
import com.google.firebase.database.*

class CourseListFragment : Fragment() {

    private lateinit var courseListRecyclerView: RecyclerView
    private lateinit var courseListLayoutManager: RecyclerView.LayoutManager
    private lateinit var courseListAdapter: CourseCardAdapter

    private lateinit var databaseReference: DatabaseReference
    private lateinit var courseList: ArrayList<Course>

    private lateinit var searchView: SearchView
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course_list, container, false)

        /**
         * Get the available courses
         */
        courseList = getCourseData() as ArrayList<Course>

        /**
         * Initialize the recycler view
         */
        courseListLayoutManager = LinearLayoutManager(view.context)
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

        return view
    }

    private fun getCourseData():List<Course>{
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        courseList = arrayListOf<Course>()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                if(snapshot.exists()){
                    for(courseSnap in snapshot.children){
                        val courseData = courseSnap.getValue(Course::class.java)
                        courseList.add(courseData!!)
                    }
                    courseListAdapter = CourseCardAdapter(courseList, view.context)
                    courseListRecyclerView.layoutManager = courseListLayoutManager
                    courseListRecyclerView.adapter = courseListAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })
        return courseList
    }
}