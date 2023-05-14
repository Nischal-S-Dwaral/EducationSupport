package com.example.educationsupport.fragment.learner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CompleteQuizAdapter
import com.example.educationsupport.adapters.learner.HomeCourseCardAdapter
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.QuizResultModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var coursesLayoutManager: RecyclerView.LayoutManager
    private lateinit var coursesAdaptor: HomeCourseCardAdapter

    private lateinit var myActivitiesRecyclerView: RecyclerView
    private lateinit var myActivitiesLayoutManager: RecyclerView.LayoutManager
    private lateinit var myActivitiesAdaptor: CompleteQuizAdapter

    private lateinit var currentUser: FirebaseUser
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false)

        /**
         * Initialise User
         */
        currentUser = FirebaseAuth.getInstance().currentUser!!

        /**
         * Set up my courses recycler view
         */
        coursesLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        coursesRecyclerView = view.findViewById(R.id.rv_course_cards)
        coursesRecyclerView.setHasFixedSize(true)
        coursesRecyclerView.layoutManager = coursesLayoutManager
        getRecommendedCourses()

        /**
         * Set up my activities recycler view
         */
        myActivitiesLayoutManager = LinearLayoutManager(view.context)
        myActivitiesRecyclerView = view.findViewById(R.id.rv_activities_cards)
        myActivitiesRecyclerView.setHasFixedSize(true)
        myActivitiesRecyclerView.layoutManager = myActivitiesLayoutManager
        getCompletedQuiz()

        return view
    }

    private fun getRecommendedCourses() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Courses")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val courseList : ArrayList<Course> = arrayListOf()
                if(snapshot.exists()){
                    for(courseSnap in snapshot.children) {
                        val courseData = courseSnap.getValue(Course::class.java)
                        courseList.add(courseData!!)
                    }
                    coursesAdaptor = if (courseList.size > 4) {
                        HomeCourseCardAdapter(
                            courseList.shuffled().take(4) as ArrayList<Course>,
                            view.context
                        )
                    } else {
                        HomeCourseCardAdapter(
                            courseList,
                            view.context
                        )
                    }
                    coursesRecyclerView.adapter = coursesAdaptor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(view.context, "Error!! ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getCompletedQuiz() {
        FirebaseDatabase.getInstance().getReference("QuizResult")
            .orderByChild("learnerId").equalTo(currentUser.uid)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val quizResults = ArrayList<QuizResultModel>()
                        for (quizResultEntry in snapshot.children) {
                            val quizResultModel = quizResultEntry.getValue(QuizResultModel::class.java)
                            if (quizResultModel != null) {
                                quizResults.add(quizResultModel)
                            }
                        }

                        myActivitiesAdaptor = if (quizResults.size > 4) {
                            CompleteQuizAdapter(
                                quizResults.takeLast(4) as ArrayList<QuizResultModel>, view.context
                            )
                        } else {
                            CompleteQuizAdapter(quizResults, view.context)
                        }
                        myActivitiesRecyclerView.adapter = myActivitiesAdaptor
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(view.context, error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}