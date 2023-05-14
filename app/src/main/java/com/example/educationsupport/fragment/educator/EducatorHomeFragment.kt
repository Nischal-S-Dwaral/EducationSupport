package com.example.educationsupport.fragment.educator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.model.Course
import com.google.firebase.database.*

class EducatorHomeFragment : Fragment() {
    private lateinit var educatorCourseListRecyclerView: RecyclerView
    private lateinit var educatorCoursesListLayoutManager: RecyclerView.LayoutManager
    private lateinit var educatorCoursesListAdaptor: EducatorCourseListCardAdapter
    private lateinit var educatorCourseList: ArrayList<Course>
    private lateinit var view: View
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_educator_courses, container, false)


        educatorCourseList = getEducatorCourses()
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
                if (snapshot.exists()) {
                    for (courseSnap in snapshot.children) {
                        val courseData = courseSnap.getValue(Course::class.java)
                        educatorCourseList.add(courseData!!)
                    }

                    educatorCoursesListAdaptor =
                        EducatorCourseListCardAdapter(educatorCourseList, view.context)

                    educatorCourseListRecyclerView.layoutManager = educatorCoursesListLayoutManager
                    educatorCourseListRecyclerView.adapter = educatorCoursesListAdaptor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        return educatorCourseList
    }
}