package com.example.educationsupport.learner

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        /**
         * Initialise
         */
        tvQuizScore = findViewById(R.id.tv_quiz_result_quiz_score)
        btnFinish = findViewById(R.id.btn_quiz_result_finish)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

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
    }
}