package com.example.educationsupport.fragment.learner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CourseCardAdapter
import com.example.educationsupport.model.Course
import com.google.firebase.database.*

class CourseListFragment : Fragment() {

    private lateinit var courseListRecyclerView: RecyclerView
    private lateinit var courseListLayoutManager: RecyclerView.LayoutManager
    private lateinit var courseListAdapter: CourseCardAdapter

    private lateinit var databaseReference: DatabaseReference

    private lateinit var searchView: SearchView
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course_list, container, false)

        /**
         * Initialize the recycler view
         */
        courseListLayoutManager = LinearLayoutManager(view.context)
        courseListRecyclerView = view.findViewById(R.id.rv_courses_list_cards)
        courseListRecyclerView.setHasFixedSize(true)
        courseListRecyclerView.layoutManager = courseListLayoutManager


        /**
         * Search view filtering
         */
        searchView = view.findViewById(R.id.search_view_learner_list)

        /**
         * Get the CourseList
         */
        getCourseListData()

        return view
    }

    private fun getCourseListData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        val courseList : ArrayList<Course> = arrayListOf()

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(courseSnap in snapshot.children){
                        val courseData = courseSnap.getValue(Course::class.java)
                        courseList.add(courseData!!)
                    }
                    courseListAdapter = CourseCardAdapter(courseList, view.context)
                    courseListRecyclerView.adapter = courseListAdapter

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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(view.context, "Error!! ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}