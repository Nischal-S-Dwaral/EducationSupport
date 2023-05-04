package com.example.educationsupport.learner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.WindowCompat
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants

/**
 * Activity to show the result of the quiz
 */
class QuizResultActivity : AppCompatActivity() {

    private lateinit var tvQuizScore: TextView
    private lateinit var btnFinish: Button
    private lateinit var btnReviewAnswer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        /**
         * Initialise
         */
        tvQuizScore = findViewById(R.id.tv_quiz_result_quiz_score)
        btnFinish = findViewById(R.id.btn_quiz_result_finish)
        btnReviewAnswer = findViewById(R.id.btn_quiz_result_review_answers)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val quizResultId = intent.getStringExtra(Constants.QUIZ_RESULT_ID)
        val courseId = intent.getStringExtra(Constants.COURSE_ID)

        val resultString = buildString {
        append(correctAnswers)
        append(" out of ")
        append(totalQuestions)
        }

        /**
         * Set the score string
         */
        tvQuizScore.text = resultString

        btnFinish.setOnClickListener {
            finish()
        }

        btnReviewAnswer.setOnClickListener {
            val intent = Intent(this@QuizResultActivity, QuizAnswerReviewActivity::class.java)
            intent.putExtra(Constants.QUIZ_RESULT_ID, quizResultId)
            intent.putExtra(Constants.COURSE_ID, courseId)
            startActivity(intent)
            finish()
        }
    }
}