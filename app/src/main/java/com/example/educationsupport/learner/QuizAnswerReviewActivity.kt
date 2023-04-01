package com.example.educationsupport.learner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.ReviewAnswerAdapter
import com.example.educationsupport.constants.ReviewAnswerConstants

class QuizAnswerReviewActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var quizScore: TextView

    private lateinit var quizAnswerReviewRecycleView: RecyclerView
    private lateinit var quizAnswerReviewLayoutManager: LinearLayoutManager
    private lateinit var quizAnswerReviewAnswerAdapter: ReviewAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_answer_review)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Get the answer object
         */
        val reviewAnswer = ReviewAnswerConstants.getReviewAnswer()

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.quiz_answer_review_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = reviewAnswer.quizTitle
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Updating the score for the quiz
         */
        quizScore = findViewById(R.id.tv_quiz_answer_review_score)
        quizScore.text = reviewAnswer.quizScore

        /**
         * Setup the quiz answers review recycler view
         */
        quizAnswerReviewLayoutManager = LinearLayoutManager(this@QuizAnswerReviewActivity)
        quizAnswerReviewAnswerAdapter = ReviewAnswerAdapter(reviewAnswer.answeredQuestion, this@QuizAnswerReviewActivity)
        quizAnswerReviewRecycleView = findViewById(R.id.rv_quiz_answer_review_list)
        quizAnswerReviewRecycleView.setHasFixedSize(true)
        quizAnswerReviewRecycleView.layoutManager = quizAnswerReviewLayoutManager
        quizAnswerReviewRecycleView.adapter = quizAnswerReviewAnswerAdapter
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