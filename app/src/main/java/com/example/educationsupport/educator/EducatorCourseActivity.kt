package com.example.educationsupport.educator

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.QuizListAdapter
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.constants.EducatorCourseConstants
import com.example.educationsupport.constants.QuizListConstants
import com.example.educationsupport.fragment.educator.QuestionCountFragment
import com.example.educationsupport.model.Quiz
import com.example.educationsupport.educator.ViewQuizAndScore

class EducatorCourseActivity : AppCompatActivity() {

    private var courseId: Int = 0
    private lateinit var toolbar: Toolbar

    private lateinit var tvDescription: TextView

    private lateinit var quizListRecyclerView: RecyclerView
    private lateinit var quizListLayoutManager: LinearLayoutManager
    private lateinit var quizListAdapter: QuizListAdapter

   private lateinit var addQuizBtn: Button
   private lateinit var test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educator_view_course)

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
        val course = EducatorCourseConstants.getEducatorCourseById(courseId)

        val quizList = QuizListConstants.getQuizListById(courseId.toString());

        addQuizBtn = findViewById(R.id.addQuizButton);

        addQuizBtn.setOnClickListener {
            val showPopUp = QuestionCountFragment()
            showPopUp.show(supportFragmentManager,"showPopUp")
        }
        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.view_course_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = course?.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Set the description
         */
        tvDescription = findViewById(R.id.tv_view_course_description)
        tvDescription.text = course?.description
        test = findViewById(R.id.Test)

        /**
         * Setup the start quiz list recycler view
         */
        quizListLayoutManager = LinearLayoutManager(this@EducatorCourseActivity)
        quizListAdapter = QuizListAdapter(quizList, this@EducatorCourseActivity)
        quizListRecyclerView = findViewById(R.id.rv_view_quiz_list)
        quizListRecyclerView.setHasFixedSize(true)
        quizListRecyclerView.layoutManager = quizListLayoutManager
        quizListRecyclerView.adapter = quizListAdapter

        test.setOnClickListener{
            val intent = Intent(this, ViewQuizAndScore::class.java)
            startActivity(intent)
        }
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