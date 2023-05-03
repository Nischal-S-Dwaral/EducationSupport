package com.example.educationsupport.learner

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.model.QuestionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TakeQuizActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentQuestion: Int = 1
    private var mQuestionsList: ArrayList<QuestionModel>? = null
    private var mSelectedOptionPosition: ArrayList<Int> = arrayListOf()
    private var mCorrectAnswers: Int = 0

    private lateinit var questionTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarTextView: TextView
    private lateinit var optionOneTextView: TextView
    private lateinit var optionTwoTextView: TextView
    private lateinit var optionThreeTextView: TextView
    private lateinit var optionFourTextView: TextView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_quiz)

        /**
         * Initialize
         */
        questionTextView = findViewById(R.id.tv_take_quiz_question)
        progressBar = findViewById(R.id.take_quiz_progress_bar)
        progressBarTextView = findViewById(R.id.tv_take_quiz_progress)
        optionOneTextView = findViewById(R.id.tv_take_quiz_option_one)
        optionTwoTextView = findViewById(R.id.tv_take_quiz_option_two)
        optionThreeTextView = findViewById(R.id.tv_take_quiz_option_three)
        optionFourTextView = findViewById(R.id.tv_take_quiz_option_four)
        submitButton = findViewById(R.id.btn_take_quiz_submit)

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        optionOneTextView.setOnClickListener(this)
        optionTwoTextView.setOnClickListener(this)
        optionThreeTextView.setOnClickListener(this)
        optionFourTextView.setOnClickListener(this)
        submitButton.setOnClickListener(this)

        /**
        * Take value passed from the Intent
        */
        val quizId = intent.getStringExtra(Constants.QUIZ_ID).toString()
        val courseId = intent.getStringExtra(Constants.COURSE_ID).toString()

        /**
         * Get the question list
         */
        getQuestionList(quizId, courseId)
    }

    private fun getQuestionList(quizId: String, courseId: String) {

        val quizDatabaseReference = FirebaseDatabase.getInstance().getReference("Quiz")
            .child(quizId).child("questionList")

        quizDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val questionList = mutableListOf<QuestionModel>()
                if (snapshot.exists()) {
                    for (questionSnapshot in snapshot.children) {
                        val question = questionSnapshot.getValue(QuestionModel::class.java)
                        if (question != null) {
                            questionList.add(question)
                        }
                    }
                    mQuestionsList = questionList as ArrayList<QuestionModel>
                    setQuestion()
                } else {
                    Toast.makeText(this@TakeQuizActivity, "Can't start this quiz!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TakeQuizActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    /**
     * Setting the question object to the layout parameters
     */
    private fun setQuestion() {

        val question = mQuestionsList!![mCurrentQuestion-1]

        /**
         * Call to set the default options view
         */
        defaultOptionsView()

        if (mCurrentQuestion == mQuestionsList!!.size) {
            submitButton.text = "FINISH"
        } else {
            submitButton.text = "SUBMIT"
        }

        progressBar.progress = mCurrentQuestion
        progressBar.max = mQuestionsList!!.size
        progressBarTextView.text = "$mCurrentQuestion"+"/"+progressBar.max

        questionTextView.text = question.question
        optionOneTextView.text = question.optionOne
        optionTwoTextView.text = question.optionTwo
        optionThreeTextView.text = question.optionThree
        optionFourTextView.text = question.optionFour

        /**
         * Making the options clickable
         */
        optionOneTextView.isClickable = true
        optionTwoTextView.isClickable = true
        optionThreeTextView.isClickable = true
        optionFourTextView.isClickable = true
    }

    /**
     * Set the default options view of the question
     */
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, optionOneTextView)
        options.add(1, optionTwoTextView)
        options.add(2, optionThreeTextView)
        options.add(3, optionFourTextView)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_quiz_option_border_bg
            )
        }
    }

    /**
     * Override the onClick method to change the view of the option when selected
     */
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_take_quiz_option_one -> {
                selectedOptionView(optionOneTextView,1)
            }
            R.id.tv_take_quiz_option_two -> {
                selectedOptionView(optionTwoTextView,2)
            }
            R.id.tv_take_quiz_option_three -> {
                selectedOptionView(optionThreeTextView,3)
            }
            R.id.tv_take_quiz_option_four -> {
                selectedOptionView(optionFourTextView,4)
            }
            R.id.btn_take_quiz_submit -> {
                /**
                 * We have to move to the next question
                 */
                if (mSelectedOptionPosition.isEmpty()) {
                    mCurrentQuestion++

                    when {
                        mCurrentQuestion <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                        /**
                         * Show the completed quiz screen and push the completed results to the DB
                         */
                        val intent = Intent(this, QuizResultActivity::class.java)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        startActivity(intent)
                        finish()
                        }
                    }
                } else {
                    /**
                     * Setting the background of the options with wrong and correct answers color
                     */
                    val question = mQuestionsList!![mCurrentQuestion-1]

                    /**
                     * Sort the correct answers and the chosen answers
                     */
                    mSelectedOptionPosition.sort()
                    val correctAnswerList = question!!.correctAnswer?.split(", ")?.sorted()
                        ?.toMutableList()

                    /**
                     * Check if the selected options match the correct options
                     */
                    var isCorrectAnswer = true
                    for (index in mSelectedOptionPosition.indices) {
                        if (mSelectedOptionPosition[index] != (correctAnswerList?.get(index)
                                ?.toInt() ?: -1)
                        ) {
                            answerView(mSelectedOptionPosition[index], R.drawable.wrong_quiz_option_border_bg)
                            isCorrectAnswer = false
                        }
                    }

                    if (isCorrectAnswer) {
                        /**
                         * Increment the correct answer count
                         */
                        mCorrectAnswers++
                    }
                    if (correctAnswerList != null) {
                        for (index in correctAnswerList.indices) {
                            answerView(correctAnswerList[index].toInt(), R.drawable.correct_quiz_option_border_bg)
                        }
                    }

                    /**
                     * Making the options non clickable
                     */
                    optionOneTextView.isClickable = false
                    optionTwoTextView.isClickable = false
                    optionThreeTextView.isClickable = false
                    optionFourTextView.isClickable = false

                    /**
                     * Changing the submit button text
                     */
                    if (mCurrentQuestion == mQuestionsList!!.size) {
                        submitButton.text = "FINISH"
                    } else {
                        submitButton.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition.clear()
                }
            }
        }
    }

    /**
     * Check with the answer and give the background
     */
    private fun answerView(answer: Int, drawableView: Int) {
        when(answer) {
            1 -> {
                optionOneTextView.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                optionTwoTextView.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                optionThreeTextView.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                optionFourTextView.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        if (mSelectedOptionPosition.contains(selectedOptionNum)) {

            tv.setTextColor(Color.parseColor("#7A8089"))
            tv.typeface = Typeface.DEFAULT
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_quiz_option_border_bg
            )
        } else {
            mSelectedOptionPosition.add(selectedOptionNum)

            tv.setTextColor(Color.parseColor("#363A43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_quiz_option_border_bg
            )
        }
    }

    /**
     * Show when the user clicks back button while taking the quiz
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.MyAlertDialogStyle))
        builder.setMessage("Are you sure you want to exit? Clicking on Yes, will clear your progress!")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id -> finish() }
            .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}