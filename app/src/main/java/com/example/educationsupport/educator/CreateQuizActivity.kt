package com.example.educationsupport.educator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.educationsupport.NotificationReceiver
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateQuizActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var progressBar: ProgressBar
    private lateinit var progressTV: TextView
    private lateinit var titleText: TextView
    private lateinit var question: EditText
    private lateinit var option1: EditText
    private lateinit var option2: EditText
    private lateinit var option3: EditText
    private lateinit var option4: EditText
    private lateinit var submitBtn: Button
    private lateinit var op1Chkbox: CheckBox
    private lateinit var op2Chkbox: CheckBox
    private lateinit var op3Chkbox: CheckBox
    private lateinit var op4Chkbox: CheckBox

    private var mCurrentQuestion: Int = 1
    private var mSelectedCorrectAnswers: ArrayList<Int> = arrayListOf()
    private var mQuestionsList: ArrayList<QuestionModel> = arrayListOf()

    private var count: Int = 0
    private var quizName: String? = null
    private var courseId: String? = null
    var currentEducatorUser: FirebaseUser? = null

    private lateinit var databaseReference: DatabaseReference //for adding quiz
    private lateinit var dbRef: DatabaseReference //to get courseId
    private lateinit var auth: FirebaseAuth

    private var scheduledQuiz: Boolean = false
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz")
        auth = FirebaseAuth.getInstance()

        currentEducatorUser = auth.currentUser

        titleText = findViewById(R.id.titleTextView)
        progressBar = findViewById(R.id.progressBar)
        progressTV = findViewById(R.id.progressTextView)
        question = findViewById(R.id.questionEditText)
        option1 = findViewById(R.id.option1EditText)
        option2 = findViewById(R.id.option2EditText)
        option3 = findViewById(R.id.option3EditText)
        option4 = findViewById(R.id.option4EditText)
        op1Chkbox = findViewById(R.id.correctOp1)
        op2Chkbox = findViewById(R.id.correctOp2)
        op3Chkbox = findViewById(R.id.correctOp3)
        op4Chkbox = findViewById(R.id.correctOp4)
        submitBtn = findViewById(R.id.submitBtn)


        count = (intent.getStringExtra("count")).toString().toInt()
        quizName = intent.getStringExtra("quizName").toString()
        courseId = intent.getStringExtra("courseId").toString()
        titleText.text = titleText.text.toString()+":" + "$quizName"
        scheduledQuiz = intent.getBooleanExtra(Constants.IS_QUIZ_SCHEDULED, false)
        startDate = intent.getStringExtra(Constants.QUIZ_START_DATE)
        endDate = intent.getStringExtra(Constants.QUIZ_END_DATE)

        setQuestion()
        op1Chkbox.setOnClickListener(this)
        op2Chkbox.setOnClickListener(this)
        op3Chkbox.setOnClickListener(this)
        op4Chkbox.setOnClickListener(this)
        submitBtn.setOnClickListener(this)
    }

    private fun setQuestion() {

        defaultOptionsView()

        if (mCurrentQuestion == count) {
            submitBtn.text = "SUBMIT"
        } else {
            submitBtn.text = "NEXT"
        }

        progressBar.progress = mCurrentQuestion
        progressBar.max = count
        progressTV.text = "$mCurrentQuestion" + "/" + progressBar.max
    }

    private fun defaultOptionsView() {

        question.text.clear()
        option1.text.clear()
        option2.text.clear()
        option3.text.clear()
        option4.text.clear()

        op1Chkbox.isChecked = false
        op2Chkbox.isChecked = false
        op3Chkbox.isChecked = false
        op4Chkbox.isChecked = false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.submitBtn -> {
                val validFields = validateFields(view)
                if(validFields) {
                    if (mSelectedCorrectAnswers.isNotEmpty()) {

                        val quesTxt = question.text.toString()
                        val opt1 = option1.text.toString()
                        val opt2 = option2.text.toString()
                        val opt3 = option3.text.toString()
                        val opt4 = option4.text.toString()
                        var correctAnswer = ""

                        for (i in 0 until mSelectedCorrectAnswers.size) {
                            correctAnswer += mSelectedCorrectAnswers[i].toString()
                            if (i < mSelectedCorrectAnswers.size - 1) {
                                correctAnswer += ", "
                            }
                        }

                        val ques = QuestionModel(
                            quesTxt,
                            opt1,
                            opt2,
                            opt3,
                            opt4,
                            correctAnswer
                        )
                        //Adding questions to List
                        mQuestionsList.add(ques)
                        mSelectedCorrectAnswers.clear()
                    }
                    if (mCurrentQuestion == count) {
                        val quizId = databaseReference.push().key!!

                        val quiz = QuizModel(
                            quizId,
                            quizName!!,
                            courseId,
                            currentEducatorUser!!.uid,
                            mQuestionsList,
                            scheduledQuiz,
                            startDate,
                            endDate
                        )

                        /**
                         * Schedule Notification
                         */
                        scheduleNotification(
                            convertStringToDate(startDate)!!,
                            "Quiz Scheduled!!",
                            "$quizName scheduled from $startDate to $endDate",
                            courseId
                        )

                        databaseReference.child(quizId).setValue(quiz)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    view.context,
                                    "Added Quiz Successfully!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, ViewQuizActivity::class.java)
                                intent.putExtra("quizId", quizId)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    view.context,
                                    "Error!! ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        mCurrentQuestion++
                        setQuestion()
                    }
                }
            }
            R.id.correctOp1 -> {
                if (op1Chkbox.isChecked)
                    mSelectedCorrectAnswers.add(1)
            }
            R.id.correctOp2 -> {
                if (op2Chkbox.isChecked)
                    mSelectedCorrectAnswers.add(2)
            }
            R.id.correctOp3 -> {
                if (op3Chkbox.isChecked)
                    mSelectedCorrectAnswers.add(3)
            }
            R.id.correctOp4 -> {
                if (op4Chkbox.isChecked)
                    mSelectedCorrectAnswers.add(4)
            }

        }
    }

    private fun validateFields(view: View): Boolean {
        var flag = true
        if(question.text.toString().isEmpty()) {
            question.error = "Question missing"
            flag = false
        }
        if(option1.text.toString().isEmpty()) {
            option1.error = "Option 1 missing"
            flag = false
        }
        if(option2.text.toString().isEmpty()) {
            option2.error = "Option 2 missing"
            flag = false
        }
        if(option3.text.toString().isEmpty()) {
            option3.error = "Option 3 missing"
            flag = false
        }
        if(option4.text.toString().isEmpty()) {
            option4.error = "Option 4 missing"
            flag = false
        }
        if(mSelectedCorrectAnswers.isEmpty()){
            Toast.makeText(view.context,"Please select correct answers",Toast.LENGTH_SHORT).show()
            flag = false
        }

        return flag

    }

    private fun convertStringToDate(dateString: String?): Date? {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            return dateString?.let { format.parse(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun scheduleNotification(date: Date, title: String, message: String, courseId: String?) {

        val databaseReference = FirebaseDatabase.getInstance().reference.child("EnrolledCourses")
        databaseReference.orderByChild("courseId").equalTo(courseId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {

                            val learnerId = data.child("learnerId").getValue(String::class.java)
                            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                            val notificationIntent = Intent(this@CreateQuizActivity, NotificationReceiver::class.java)
                            notificationIntent.putExtra("title", title)
                            notificationIntent.putExtra("message", message)
                            notificationIntent.putExtra("userId", learnerId)

                            val pendingIntent = PendingIntent.getBroadcast(
                                this@CreateQuizActivity, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )

//                            val calendar = Calendar.getInstance()
//                            calendar.add(Calendar.MINUTE, 1)
//                            val date1 = calendar.time

                            alarmManager.setExact(
                                AlarmManager.RTC_WAKEUP,
                                date.time,
                                pendingIntent
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@CreateQuizActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }
}