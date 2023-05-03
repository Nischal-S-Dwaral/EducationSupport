package com.example.educationsupport.educator

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.QuizQuestionAdapter
import com.example.educationsupport.model.QuestionModel
import com.example.educationsupport.model.QuizModel
import com.google.firebase.database.*

class ViewQuizActivity : AppCompatActivity() {
    lateinit var okButton: Button
    private lateinit var quizQuestionRecyclerView: RecyclerView
    private lateinit var quizQuestionLayoutManager: LinearLayoutManager
    private lateinit var quizQuestionAdapter: QuizQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz)

        val currentQuizId = intent.getStringExtra("quizId")
        print(currentQuizId)
        okButton = findViewById(R.id.OKbutton)
        var questions = ArrayList<QuestionModel>()

        val quizDatabaseReference = FirebaseDatabase.getInstance().reference.child("Quiz").child(currentQuizId!!)
        quizDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val quiz = snapshot.getValue(QuizModel::class.java)
                    var questionList:List<QuestionModel> = quiz!!.questionList
                    //Setting up quiz question adaptor
                    quizQuestionLayoutManager = LinearLayoutManager(this@ViewQuizActivity)
                    quizQuestionAdapter = QuizQuestionAdapter(questionList, this@ViewQuizActivity)
                    quizQuestionRecyclerView = findViewById(R.id.rv_view_quiz)
                    quizQuestionRecyclerView.setHasFixedSize(true)
                    quizQuestionRecyclerView.layoutManager = quizQuestionLayoutManager
                    quizQuestionRecyclerView.adapter = quizQuestionAdapter

                    okButton.setOnClickListener{
                        val intent = Intent(this@ViewQuizActivity, EducatorMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }
        })




    }
}