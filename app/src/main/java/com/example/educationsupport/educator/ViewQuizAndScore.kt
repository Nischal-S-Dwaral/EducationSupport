package com.example.educationsupport.educator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerScoreAdapter
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.constants.QuizListConstants
import com.example.educationsupport.educator.ViewQuizActivity
import com.example.educationsupport.model.Learner
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry

class ViewQuizAndScore : AppCompatActivity() {

    //lateinit var viewbutton: Button
    private var quizId: Int = 0

    private lateinit var tvLearnerQuizScore: TextView
    private lateinit var titleText: TextView

    private lateinit var learnerScoreRecyclerView: RecyclerView
    private lateinit var learnerScoreLayoutManager: LinearLayoutManager
    private lateinit var learnerScoreAdapter: LearnerScoreAdapter
    private var quizName: String? = null
    private lateinit var barList: ArrayList<BarEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz_and_score)
        titleText = findViewById(R.id.quiz_name)
        //quizId = intent.getIntExtra(Constants.QUIZ_ID, -1)
        //viewbutton = findViewById(R.id.viewbutton)

        barList = ArrayList()
        barList.add(BarEntry(10f,500f))
        barList.add(BarEntry(20f,100f))
        barList.add(BarEntry(30f,300f))
        barList.add(BarEntry(40f,800f))

        /**
         * Get the quiz information
         */
        //val quiz = QuizListConstants.getQuizById(quizId)

        val quizList = QuizListConstants.getQuizList()
        val firstQuiz = quizList.first()
        val learnerlist = firstQuiz.learnerList
        quizName = intent.getStringExtra("quizName").toString()
        titleText.text = titleText.text.toString() + "$quizName"

        /**
         * Setup the start quiz list recycler view
         */
        learnerScoreLayoutManager = LinearLayoutManager(this@ViewQuizAndScore)
        learnerScoreAdapter = LearnerScoreAdapter(learnerlist, this@ViewQuizAndScore)
        learnerScoreRecyclerView = findViewById(R.id.view_quiz_learner_score)
        learnerScoreRecyclerView.setHasFixedSize(true)
        learnerScoreRecyclerView.layoutManager = learnerScoreLayoutManager
        learnerScoreRecyclerView.adapter = learnerScoreAdapter


//        viewbutton.setOnClickListener{
//            val intent = Intent(this, ViewQuizActivity::class.java)
//            startActivity(intent)
//        }
    }
}