package com.example.educationsupport.fragment.educator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.constants.EducatorCourseConstants
import com.example.educationsupport.model.Course
import com.google.firebase.database.*


class EducatorCoursesFragment : Fragment() {

    private lateinit var educatorCourseListRecyclerView: RecyclerView
    private lateinit var educatorCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var educatorCoursesListAdaptor: EducatorCourseListCardAdapter
    private lateinit var educatorCourseList: ArrayList<Course>
    private lateinit var view: View

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
        val educatorCourseList = getEducatorCourses()
        /**
         * Set up my courses recycler view
         */



        return view
    }

    private fun getEducatorCourses(): ArrayList<Course> {
//        myCourseListRecyclerView.visibility = View.GONE

        val dbRef = FirebaseDatabase.getInstance().getReference( "Courses" )
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val  educatorCourseList = ArrayList<Course>()
                if (snapshot.exists()) {
                    for (courseSnapshot in snapshot.children) { //iterate through the courses
                        val course = courseSnapshot.getValue(Course::class.java)
                        if (course != null) {
                            educatorCourseList.add(course)
                        }
                    }
                    educatorCoursesListLayoutManager = LinearLayoutManager(view.context)
                    educatorCoursesListAdaptor = EducatorCourseListCardAdapter(educatorCourseList, view.context)
                    educatorCourseListRecyclerView = view.findViewById(R.id.rv_educator_courses_list_cards)
                    educatorCourseListRecyclerView.setHasFixedSize(true)
                    educatorCourseListRecyclerView.layoutManager = educatorCoursesListLayoutManager
                    educatorCourseListRecyclerView.adapter = educatorCoursesListAdaptor
//                    myCoursesListAdaptor = EducatorCourseListCardAdapter(educatorCourseList, view.context)
//                    myCourseListRecyclerView.adapter = myCoursesListAdaptor
                    //give me code to print the educatorCourseList
//                    educatorCourseList.forEach { println(it) }

//                    myCourseListRecyclerView.visibility = View.VISIBLE
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }

        })
        return educatorCourseList
    }
}