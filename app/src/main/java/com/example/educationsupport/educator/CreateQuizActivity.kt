package com.example.educationsupport.educator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.educationsupport.R
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.Question
import com.example.educationsupport.model.QuestionModel
import com.example.educationsupport.model.QuizModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateQuizActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var progressBar: ProgressBar
    lateinit var progressTV: TextView
    lateinit var titleText: TextView
    lateinit var question: EditText
    lateinit var option1: EditText
    lateinit var option2: EditText
    lateinit var option3: EditText
    lateinit var option4: EditText
    lateinit var correctAnswer: EditText
    lateinit var submitBtn: Button
    private var currentPosition = 0;
    private var count: Int = 0
    private var quizName: String? = null
    private var courseName: String? = null
    val questions = ArrayList<QuestionModel>()
    var currentEducatorUser: FirebaseUser? = null

    private lateinit var op1Chkbox: CheckBox
    private lateinit var op2Chkbox: CheckBox
    private lateinit var op3Chkbox: CheckBox
    private lateinit var op4Chkbox: CheckBox

    private var currentQuestion: Int = 1

    private var correctAnswers: ArrayList<Int> = arrayListOf()

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

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
        //correctAnswer = findViewById(R.id.correctAnsEditText)

        op1Chkbox = findViewById(R.id.correctOp1)
        op2Chkbox = findViewById(R.id.correctOp2)
        op3Chkbox = findViewById(R.id.correctOp3)
        op4Chkbox = findViewById(R.id.correctOp4)

        submitBtn = findViewById(R.id.submitBtn)


        val intent = intent
        count = (intent.getStringExtra("count")).toString().toInt()
        quizName = intent.getStringExtra("quizName").toString()
        courseName = intent.getStringExtra("courseName").toString()
        titleText.text = titleText.text.toString() + "$quizName"
        //currentPosition = 1;


        progressBar.max = count
        progressTV.text = "$currentPosition" + "/" + count

        progressBar.progress = currentPosition

        op1Chkbox.setOnClickListener(this)
        op2Chkbox.setOnClickListener(this)
        op3Chkbox.setOnClickListener(this)
        op4Chkbox.setOnClickListener(this)

        submitBtn.setOnClickListener(this)

        setQuestion()

    }

    private fun setQuestion() {
        question.text.clear()
        option1.text.clear()
        option2.text.clear()
        option3.text.clear()
        option4.text.clear()
        //correctAnswer.text.clear()
        op1Chkbox.isChecked = false
        op2Chkbox.isChecked = false
        op3Chkbox.isChecked = false
        op4Chkbox.isChecked = false
        correctAnswers.clear()

        if (currentPosition == count) {
            submitBtn.text = "SUBMIT"

        } else {
            submitBtn.text = "NEXT"
        }
        currentPosition += 1;
        progressBar.progress = currentPosition;
        progressTV.text = "$currentPosition" + "/" + count

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.submitBtn -> {
                if (correctAnswers.isNotEmpty()) {
                    val quesTxt = question.text.toString()
                    val opt1 = option1.text.toString()
                    val opt2 = option2.text.toString()
                    val opt3 = option3.text.toString()
                    val opt4 = option4.text.toString()

                    val ques = QuestionModel(
                        quesTxt,
                        opt1,
                        opt2,
                        opt3,
                        opt4,
                        correctAnswers
                    )
                    //Adding questions to List
                    questions.add(ques)
                    correctAnswers.clear()
                }
                if(currentQuestion == count){
                    val quizId = databaseReference.push().key!!

                    val quiz = QuizModel(quizId,quizName!!,"-NURpVh7jtMu5SgkmQFk", currentEducatorUser!!.uid, questions)

                    databaseReference.child(quizId).setValue(quiz)
                        .addOnSuccessListener {
                            Toast.makeText(view!!.context, "Added Quiz Successfully!!", Toast.LENGTH_SHORT)
                                .show()

                        }.addOnFailureListener {
                            Toast.makeText(view!!.context, "Error!! ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    val intent = Intent(this, ViewQuizActivity::class.java)
                    intent.putExtra("quizId",quizId);
                    startActivity(intent)
                    finish()
                }
                else{
                    currentQuestion++;
                    setQuestion()
                }
            }
            R.id.correctOp1 -> {
               if(op1Chkbox.isChecked)
                   correctAnswers.add(1)
            }
            R.id.correctOp2 -> {
                if(op2Chkbox.isChecked)
                    correctAnswers.add(2)
            }
            R.id.correctOp3 -> {
                if(op3Chkbox.isChecked)
                    correctAnswers.add(3)
            }
            R.id.correctOp4 -> {
                if(op4Chkbox.isChecked)
                    correctAnswers.add(4)
            }

        }
    }


}