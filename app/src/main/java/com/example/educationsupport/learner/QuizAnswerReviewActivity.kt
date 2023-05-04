package com.example.educationsupport.learner

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.ReviewAnswerAdapter
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.model.QuizResultModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizAnswerReviewActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var quizScore: TextView

    private lateinit var quizAnswerReviewRecycleView: RecyclerView
    private lateinit var quizAnswerReviewLayoutManager: LinearLayoutManager
    private lateinit var quizAnswerReviewAnswerAdapter: ReviewAnswerAdapter

    private var quizResultId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_answer_review)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.quiz_answer_review_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Review Answers"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Updating the score for the quiz
         */
        quizScore = findViewById(R.id.tv_quiz_answer_review_score)

        /**
         * Setup the quiz answers review recycler view
         */
        quizAnswerReviewLayoutManager = LinearLayoutManager(this@QuizAnswerReviewActivity)
        quizAnswerReviewRecycleView = findViewById(R.id.rv_quiz_answer_review_list)
        quizAnswerReviewRecycleView.setHasFixedSize(true)
        quizAnswerReviewRecycleView.layoutManager = quizAnswerReviewLayoutManager

        quizResultId = intent.getStringExtra(Constants.QUIZ_RESULT_ID)

        getQuizResultData(quizResultId)
    }

    private fun getQuizResultData(quizResultId: String?) {

        if (quizResultId != null) {
            FirebaseDatabase.getInstance().getReference("QuizResult").child(quizResultId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val quizResult = snapshot.getValue(QuizResultModel::class.java)

                            if (quizResult != null) {
                                quizScore.text = "Your Score: ${quizResult.correctAnswerScore} out of ${quizResult.totalQuestionScore}"

                                quizAnswerReviewAnswerAdapter = ReviewAnswerAdapter(quizResult.answeredQuestionList, this@QuizAnswerReviewActivity)
                                quizAnswerReviewRecycleView.adapter = quizAnswerReviewAnswerAdapter
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@QuizAnswerReviewActivity, error.message, Toast.LENGTH_SHORT).show()
                    }
                })
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