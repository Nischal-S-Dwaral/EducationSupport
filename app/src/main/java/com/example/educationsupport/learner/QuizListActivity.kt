package com.example.educationsupport.learner

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.QuizListCardAdapter
import com.example.educationsupport.constants.QuizListConstants

class QuizListActivity : AppCompatActivity() {

    private lateinit var courseName: String
    private lateinit var toolbar: Toolbar

    private lateinit var quizListRecyclerView: RecyclerView
    private lateinit var quizListLayoutManager: LinearLayoutManager
    private lateinit var quizListAdapter: QuizListCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list)

        /**
         * Take value passed from the Intent
         */
        courseName = intent.getStringExtra("courseTitle").toString()

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.quiz_list_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = courseName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Get the quiz list
         */
        val quizList = QuizListConstants.getQuizList()

        /**
         * Setup the quiz list recycler view
         */
        quizListLayoutManager = LinearLayoutManager(this@QuizListActivity)
        quizListAdapter = QuizListCardAdapter(quizList, this@QuizListActivity)
        quizListRecyclerView = findViewById(R.id.rv_quiz_list)
        quizListRecyclerView.setHasFixedSize(true)
        quizListRecyclerView.layoutManager = quizListLayoutManager
        quizListRecyclerView.adapter = quizListAdapter
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