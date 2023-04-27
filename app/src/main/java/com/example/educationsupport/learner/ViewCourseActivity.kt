package com.example.educationsupport.learner

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
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
import com.example.educationsupport.model.Quiz

class ViewCourseActivity : AppCompatActivity() {

    private var courseId: Int = 0
    private lateinit var toolbar: Toolbar

    private lateinit var tvDescription: TextView

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
        courseId = intent.getIntExtra(Constants.COURSE_ID, -1)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Get the course information
         */
        val course = ViewCourseConstants.getCourse()

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.view_course_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = course.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Set the description
         */
        tvDescription = findViewById(R.id.tv_view_course_description)
        tvDescription.text = course.description

        val quizList = course.quizList


        val startQuizList = filterQuizList(quizList, false)
        val completedQuizList = filterQuizList(quizList, true)

        /**
         * Setup the start quiz list recycler view
         */
        startQuizListLayoutManager = LinearLayoutManager(this@ViewCourseActivity)
        startQuizListAdapter = StartQuizAdapter(startQuizList, this@ViewCourseActivity)
        startQuizListRecyclerView = findViewById(R.id.rv_view_course_quiz_list)
        startQuizListRecyclerView.setHasFixedSize(true)
        startQuizListRecyclerView.layoutManager = startQuizListLayoutManager
        startQuizListRecyclerView.adapter = startQuizListAdapter

        /**
         * Setup the completed quiz list recycler view
         */
        completedQuizListLayoutManager = LinearLayoutManager(this@ViewCourseActivity)
        completedQuizListAdapter = CompleteQuizAdapter(completedQuizList, this@ViewCourseActivity)
        completedQuizListRecyclerView = findViewById(R.id.rv_view_course_completed_quiz_list)
        completedQuizListRecyclerView.setHasFixedSize(true)
        completedQuizListRecyclerView.layoutManager = completedQuizListLayoutManager
        completedQuizListRecyclerView.adapter = completedQuizListAdapter
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