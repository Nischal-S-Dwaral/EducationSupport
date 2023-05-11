package com.example.educationsupport.fragment.educator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerListCardAdapter
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddNewLearnerFragment : Fragment() {

    private lateinit var searchView: SearchView

    private lateinit var learnerListRecyclerView: RecyclerView
    private lateinit var learnerListLayoutManager: RecyclerView.LayoutManager
    private lateinit var learnerListAdapter: LearnerListCardAdapter

    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.educator_add_learner, container, false)

        learnerListLayoutManager = LinearLayoutManager(view.context)
        learnerListRecyclerView = view.findViewById(R.id.rv_learner_list_cards)
        learnerListRecyclerView.setHasFixedSize(true)
        learnerListRecyclerView.layoutManager = learnerListLayoutManager

        searchView = view.findViewById(R.id.search_view_add_learner_list)

        getAllCourses()

        return view
    }

    private fun getAllCourses() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Courses")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allCourseList = ArrayList<Course>()
                if (snapshot.exists()) {
                    for (courseSnapshot in snapshot.children) { //iterate through the courses
                        val course = courseSnapshot.getValue(Course::class.java)
                        if (course != null) {
                            allCourseList.add(course)
                        }
                    }

                    val allCourseName = allCourseList.map { it.name }

                    // access the spinner
                    val spinner = view.findViewById<Spinner>(R.id.select_course_spinner)
                    if (spinner != null) {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item, allCourseName
                        )
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View, position: Int, id: Long
                            ) {
                                Toast.makeText(
                                    view.context,
                                    getString(R.string.selected_item) + " " +
                                            "" + allCourseName[position], Toast.LENGTH_SHORT
                                ).show()
                                getLearnerListData(allCourseList[position])
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                            }
                        }
                    }
                    allCourseList.forEach { println(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        view.context,
                        error.message, Toast.LENGTH_SHORT
                    ).show()
            }
        })
    }


    private fun getLearnerListData(course: Course) {

        FirebaseDatabase.getInstance().getReference("Users")
            .orderByChild("educator").equalTo(false)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val learnerList: ArrayList<Users> = arrayListOf()
                    if (snapshot.exists()) {
                        for (learnerSnap in snapshot.children) {
                            val learnerData = learnerSnap.getValue(Users::class.java)
                            if (learnerData != null) {
                                learnerList.add(learnerData)
                            }
                        }

                        learnerListAdapter =
                            LearnerListCardAdapter(learnerList, view.context, course)
                        learnerListRecyclerView.adapter = learnerListAdapter

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                            override fun onQueryTextSubmit(query: String?): Boolean {
                                learnerListAdapter.filter.filter(query)
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                learnerListAdapter.filter.filter(newText)
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