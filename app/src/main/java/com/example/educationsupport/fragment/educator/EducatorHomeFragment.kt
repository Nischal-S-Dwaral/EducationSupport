package com.example.educationsupport.fragment.educator

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.adapters.learner.CourseCardAdapter
import com.example.educationsupport.constants.EducatorCourseConstants
import com.example.educationsupport.model.Course
import com.google.firebase.database.*

class EducatorHomeFragment : Fragment() {
    private lateinit var educatorCourseListRecyclerView: RecyclerView
    private lateinit var educatorCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var educatorCoursesListAdaptor: EducatorCourseListCardAdapter
    private lateinit var educatorCourseList: ArrayList<Course>
    private lateinit var view: View
    private lateinit var databaseReference: DatabaseReference

//    private lateinit var courseListRecyclerView: RecyclerView
//    private lateinit var courseListLayoutManager: RecyclerView.LayoutManager
//    private lateinit var courseListAdapter: CourseCardAdapter
//
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var courseList: ArrayList<Course>
//
//    private lateinit var searchView: SearchView
//    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_educator_courses, container, false)

        /**
         * Get the educator course list
         */
//        val educatorCourseList = EducatorCourseConstants.educatorCourseList();
//        val educatorCourseList = getEducatorCourses()

        educatorCourseList = getEducatorCourses() as ArrayList<Course>
        /**
         * Set up my courses recycler view
         */

        educatorCoursesListLayoutManager = LinearLayoutManager(view.context)
        educatorCourseListRecyclerView = view.findViewById(R.id.rv_educator_courses_list_cards)
        educatorCourseListRecyclerView.setHasFixedSize(true)

        return view
    }

    private fun getEducatorCourses(): ArrayList<Course> {
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        educatorCourseList = arrayListOf<Course>()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                educatorCourseList.clear()
                if(snapshot.exists()){
                    for(courseSnap in snapshot.children){
                        val courseData = courseSnap.getValue(Course::class.java)
                        educatorCourseList.add(courseData!!)
                    }

                    educatorCoursesListAdaptor = EducatorCourseListCardAdapter(educatorCourseList, view.context)

                    educatorCourseListRecyclerView.layoutManager = educatorCoursesListLayoutManager
                    educatorCourseListRecyclerView.adapter = educatorCoursesListAdaptor
//                    myCoursesListAdaptor = EducatorCourseListCardAdapter(educatorCourseList, view.context)
//                    myCourseListRecyclerView.adapter = myCoursesListAdaptor
                    //give me code to print the educatorCourseList
                    educatorCourseList.forEach { println(it) }

//                    myCourseListRecyclerView.visibility = View.VISIBLE
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })
        return educatorCourseList
    }
}