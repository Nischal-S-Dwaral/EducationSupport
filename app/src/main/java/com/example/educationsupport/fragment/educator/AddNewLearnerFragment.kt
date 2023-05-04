package com.example.educationsupport.fragment.educator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
//import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerListCardAdapter
import com.example.educationsupport.constants.LearnerListConstants
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.Users
import com.google.firebase.database.*


class AddNewLearnerFragment : Fragment() {

    // creating array list for listview
    lateinit var programmingLanguagesList: ArrayList<String>;

    // creating variable for searchview
    private lateinit var searchView: SearchView

    private lateinit var learnerListRecyclerView: RecyclerView
    private lateinit var learnerListLayoutManager: RecyclerView.LayoutManager
    private lateinit var learnerListAdapter: LearnerListCardAdapter
    private lateinit var databaseReference: DatabaseReference

    private lateinit var allCourseList: ArrayList<Course>

    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val learnerList = LearnerListConstants.getLearnerList()

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.educator_add_learner, container, false)



        val languages = resources.getStringArray(R.array.Languages)

//        val templanguages = getAllCourses()
        getAllCourses()



//        getLearnerListData()

        /**
         * Search view filtering
         */

        return view
    }

    private fun getAllCourses() {
        val dbRef = FirebaseDatabase.getInstance().getReference( "Courses" )
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

                    learnerListLayoutManager = LinearLayoutManager(view.context)
                    learnerListRecyclerView = view.findViewById(R.id.rv_learner_list_cards)
                    learnerListRecyclerView.setHasFixedSize(true)
                    learnerListRecyclerView.layoutManager = learnerListLayoutManager

                    searchView = view.findViewById(R.id.search_view_add_learner_list)

                    val allCourseName = allCourseList.map { it.name }

                    // access the spinner
                    val spinner = view.findViewById<Spinner>(R.id.select_course_spinner)
                    if (spinner != null) {
                        val adapter = ArrayAdapter(requireContext(),
                            android.R.layout.simple_spinner_item, allCourseName)
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>,
                                                        view: View, position: Int, id: Long) {
                                Toast.makeText(
                                    view.context,
                                    getString(R.string.selected_item) + " " +
                                            "" + allCourseName[position], Toast.LENGTH_SHORT).show()
                                getLearnerListData(allCourseList[position])
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                                // write code to perform some action
                            }
                        }
                    }
                    allCourseList.forEach { println(it) }

//                    myCourseListRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }

        })
//        return allCourseList
    }


    private fun getLearnerListData(course: Course) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        val learnerList : ArrayList<Users> = arrayListOf()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(learnerSnap in snapshot.children){
                        val learnerData = learnerSnap.getValue(Users::class.java)
                        if (learnerData != null){
                            if (!learnerData.isEducator!!){
                                learnerList.add(learnerData)
                            }
                        }
                    }

                    learnerListAdapter = LearnerListCardAdapter(learnerList, view.context, course)
                    learnerListRecyclerView.adapter = learnerListAdapter

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            learnerListAdapter.filter.filter(query)
                            println("printing learner list")
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            learnerListAdapter.filter.filter(newText)
                            println("printing learner list")
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



//    }
//}