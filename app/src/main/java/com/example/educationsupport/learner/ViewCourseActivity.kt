package com.example.educationsupport.learner

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CompleteQuizAdapter
import com.example.educationsupport.adapters.learner.StartQuizAdapter
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.constants.ViewCourseConstants
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.EnrolledCourse
import com.example.educationsupport.model.Quiz
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewCourseActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tvDescription: TextView
    private lateinit var tvTakeQuizHeader: TextView
    private lateinit var tvCompletedQuizResultHeader: TextView

    private lateinit var enrollButton: Button
    private lateinit var currentUser: FirebaseUser

    private lateinit var startQuizListRecyclerView: RecyclerView
    private lateinit var startQuizListLayoutManager: LinearLayoutManager
    private lateinit var startQuizListAdapter: StartQuizAdapter

    private lateinit var completedQuizListRecyclerView: RecyclerView
    private lateinit var completedQuizListLayoutManager: LinearLayoutManager
    private lateinit var completedQuizListAdapter: CompleteQuizAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        /**
         * Take value passed from the Intent
         */
        val courseId = intent.getStringExtra(Constants.COURSE_ID).toString()

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.view_course_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Set the text views and buttons
         */
        tvDescription = findViewById(R.id.tv_view_course_description)
        tvTakeQuizHeader = findViewById(R.id.tv_view_course_learner_take_quiz)
        tvCompletedQuizResultHeader = findViewById(R.id.tv_view_course_learner_completed_quiz_result)
        enrollButton = findViewById(R.id.btn_view_course_activity_enroll)

        /**
         * Initialise firebase auth
         */
        currentUser = FirebaseAuth.getInstance().currentUser!!

        /**
         * Initialise start quiz list recycler view
         */
        startQuizListLayoutManager = LinearLayoutManager(this@ViewCourseActivity)
        startQuizListRecyclerView = findViewById(R.id.rv_view_course_quiz_list)
        startQuizListRecyclerView.setHasFixedSize(true)
        startQuizListRecyclerView.layoutManager = startQuizListLayoutManager

        /**
         * Initialise the completed quiz list recycler view
         */
        completedQuizListLayoutManager = LinearLayoutManager(this@ViewCourseActivity)
        completedQuizListRecyclerView = findViewById(R.id.rv_view_course_completed_quiz_list)
        completedQuizListRecyclerView.setHasFixedSize(true)
        completedQuizListRecyclerView.layoutManager = completedQuizListLayoutManager

        /**
         * Get the data of the course
         */
        getCourseData(courseId)
    }

    private fun getCourseData(courseId: String) {

        val courseDatabaseReference = FirebaseDatabase.getInstance().reference.child("Courses").child(courseId)
        courseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val course = snapshot.getValue(Course::class.java)!!

                    supportActionBar!!.title = course.name
                    tvDescription.text = course.description

                    /**
                     * Check if the user is enrolled or not.
                     */
                    checkIfLearnerEnrolledCourse(course)

                    //TODO: Get Quiz List
                    val quizList = ViewCourseConstants.getCourse().quizList
                    val startQuizList = filterQuizList(quizList, false)

                    /**
                     * Setup the start quiz list recycler view
                     */
                    //TODO: This should be inside get quiz list and firebase implementation
                    startQuizListAdapter = StartQuizAdapter(startQuizList, this@ViewCourseActivity)
                    startQuizListRecyclerView.adapter = startQuizListAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewCourseActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkIfLearnerEnrolledCourse(course: Course) {

        val enrolledCourseDatabaseReference = FirebaseDatabase.getInstance().getReference("EnrolledCourses")
        val checkIfEnrolledCourseQuery = enrolledCourseDatabaseReference
            .orderByChild("courseId").equalTo(course.id)

        checkIfEnrolledCourseQuery.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //TODO: Get Completed Quiz List
                    val quizList = ViewCourseConstants.getCourse().quizList
                    val completedQuizList = filterQuizList(quizList, true)

                    /**
                     * Setup the completed quiz list recycler view
                     */
                    //TODO: This should be inside get quiz result list and firebase implementation
                    completedQuizListAdapter = CompleteQuizAdapter(completedQuizList, this@ViewCourseActivity)
                    completedQuizListRecyclerView.adapter = completedQuizListAdapter
                } else {
                    /**
                    * If not enrolled, don't need to show the completed quiz list
                    */
                    tvCompletedQuizResultHeader.visibility = View.GONE
                    enrollButton.visibility = View.VISIBLE

                    enrollButton.setOnClickListener {

                        val builder = AlertDialog.Builder(this@ViewCourseActivity)
                        builder.setMessage("Are you sure you want to enroll?")
                            .setCancelable(true)
                            .setPositiveButton("Yes") { dialog, id ->

                                val enrolledCourseId = enrolledCourseDatabaseReference.push().key!!
                                val enrolledCourse = EnrolledCourse(
                                    enrolledCourseId,
                                    course.name,
                                    course.description,
                                    course.id,
                                    currentUser.uid
                                )

                                enrolledCourseDatabaseReference.child(enrolledCourseId).setValue(enrolledCourse)
                                    .addOnSuccessListener {
                                        Toast.makeText(this@ViewCourseActivity, "Enrolled Course Successfully!!", Toast.LENGTH_SHORT)
                                            .show()

                                        /**
                                         * Reload the activity once enrollment is successful
                                         */
                                        recreate()

                                    }.addOnFailureListener {
                                        Toast.makeText(this@ViewCourseActivity, "Error!! ${it.message}", Toast.LENGTH_SHORT)
                                            .show()
                                    }

                            }
                        val alert = builder.create()
                        alert.show()

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewCourseActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterQuizList(quizList: List<Quiz>, isQuizCompleted : Boolean): List<Quiz> {
        return quizList.filter {
            quiz -> quiz.isCompleted == isQuizCompleted
        }
    }

    /**
     * Handle the event of back button pressed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}