package com.example.educationsupport.educator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.constants.QuestionsConstants
import com.example.educationsupport.model.Question

class ViewQuizActivity : AppCompatActivity() {
    var questions = ArrayList<Question>()
    var quizName: String = ""
    lateinit var displayTV: TextView
    lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz)

        questions = QuestionsConstants.getQuestions();
        displayTV = findViewById(R.id.displayTV)
        okButton = findViewById(R.id.OKbutton)

        var quizText: String = ""
        for(q in questions){
            quizText += "\n"+"Question "+"${q.id}"+" ${q.question}"+"\n"+
                        "Option 1: "+" ${q.optionOne}"+"\n"+
                        "Option 2: "+" ${q.optionTwo}"+"\n"+
                        "Option 3: "+" ${q.optionThree}"+"\n"+
                        "Option 4: "+" ${q.optionFour}"+"\n"+
                        "Correct Answer -> "+" ${q.correctAnswer}"+"\n"
        }

        displayTV.text = quizText

        okButton.setOnClickListener{
            val intent = Intent(this, EducatorMainActivity::class.java)
            startActivity(intent)
        }
    }
}