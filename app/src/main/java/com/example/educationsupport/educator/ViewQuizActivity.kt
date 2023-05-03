package com.example.educationsupport.educator

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.constants.QuestionsConstants
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.Question
import com.example.educationsupport.model.QuestionModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ViewQuizActivity : AppCompatActivity() {
    //var questions = ArrayList<QuestionModel>()
    var quizName: String = ""
    lateinit var displayTV: TextView
    lateinit var okButton: Button
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz)

        val currentQuizId = intent.getStringExtra("quizId")
        print(currentQuizId)
       // questions = QuestionsConstants.getQuestions();
        displayTV = findViewById(R.id.displayTV)
        okButton = findViewById(R.id.OKbutton)
        var questions = ArrayList<QuestionModel>()
//        databaseReference = FirebaseDatabase.getInstance().reference.child("Quiz").child(currentQuizId!!)
//            databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    var questions = snapshot.child("questionList").getValue(QuestionModel::class.java)
//
//                    var quizText: String = ""
//                    var cnt = 1;
//                    for(q in questions){
//                        quizText += "\n"+"Question "+"${cnt}"+" ${q.question}"+"\n"+
//                                "Option 1: "+" ${q.optionOne}"+"\n"+
//                                "Option 2: "+" ${q.optionTwo}"+"\n"+
//                                "Option 3: "+" ${q.optionThree}"+"\n"+
//                                "Option 4: "+" ${q.optionFour}"+"\n"+
//                                "Correct Answer -> "+" ${q.correctAnswer}"+"\n";
//                        cnt+=1
//                    }
//
//                    displayTV.text = quizText
//
//                    okButton.setOnClickListener{
//                        val intent = Intent(this@ViewQuizActivity, EducatorMainActivity::class.java)
//                        startActivity(intent)
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
//            }
//        })




    }
}