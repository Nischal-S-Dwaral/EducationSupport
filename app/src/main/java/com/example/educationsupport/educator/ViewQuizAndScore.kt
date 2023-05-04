package com.example.educationsupport.educator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerScoreAdapter
import com.example.educationsupport.constants.QuizListConstants
import com.example.educationsupport.model.LearnerScore
import com.example.educationsupport.model.QuizResultModel
import com.example.educationsupport.model.Users
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewQuizAndScore : AppCompatActivity() {

    //lateinit var viewbutton: Button
    private var quizId: String? = null

    private lateinit var tvLearnerQuizScore: TextView
    private lateinit var titleText: TextView
    private lateinit var  toolbar: Toolbar

    private lateinit var learnerScoreRecyclerView: RecyclerView
    private lateinit var learnerScoreLayoutManager: LinearLayoutManager
    private lateinit var learnerScoreAdapter: LearnerScoreAdapter
    private lateinit var barList: ArrayList<BarEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz_and_score)

        toolbar = findViewById(R.id.view_quiz_and_score_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        BarChart barChart = findViewById(R.id.barChart)
//        getData()
//        BarDataSet barDataSet = new BarDataSet(barList, "Learner score")
//        BarData barData = new BarData(barDataSet)
//        barChart.setData(barData)


        /**
         * Setup the start quiz list recycler view
        */
        learnerScoreLayoutManager = LinearLayoutManager(this@ViewQuizAndScore)
        learnerScoreRecyclerView = findViewById(R.id.view_quiz_learner_score)
        learnerScoreRecyclerView.setHasFixedSize(true)
        learnerScoreRecyclerView.layoutManager = learnerScoreLayoutManager

        quizId = intent.getStringExtra("quizId").toString()
        //titleText.text = quizName
        getQuizResultData(quizId)

    }

    private fun getQuizResultData(quizId: String?) {
        if (quizId != null) {
            FirebaseDatabase.getInstance().getReference("QuizResult").orderByChild("quizId").equalTo(quizId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val learnerScorelist = ArrayList<LearnerScore>()
                        if (snapshot.exists()) {
                            for (data in snapshot.children) {
                                val quizResult = data.getValue(QuizResultModel::class.java)
                                if(quizResult != null) {
                                    val countCorrectAnswer = quizResult.correctAnswerScore ?: 0
                                    val countTotalQuestion = quizResult.totalQuestionScore ?: 0
                                    val quizScore = countCorrectAnswer * 100/countTotalQuestion
                                    supportActionBar!!.title = quizResult.quizName

                                    if (quizResult.learnerId != null) {
                                        FirebaseDatabase.getInstance().getReference("Users")
                                            .child(quizResult.learnerId)
                                            .addListenerForSingleValueEvent(object: ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    if(snapshot.exists()){
                                                        val user = snapshot.getValue(Users::class.java)

                                                        val learnerScore = LearnerScore(
                                                            user?.uid,
                                                            quizScore,
                                                            quizResult.id
                                                        )
                                                        learnerScorelist.add(learnerScore)
                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }

                                            })
                                    }
                                }
                            }
                            learnerScoreAdapter = LearnerScoreAdapter(learnerScorelist, this@ViewQuizAndScore)
                            learnerScoreRecyclerView.adapter = learnerScoreAdapter
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@ViewQuizAndScore, error.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    fun getData() {
        barList = ArrayList()
        barList.add(BarEntry(10f,10f))
        barList.add(BarEntry(20f,20f))
        barList.add(BarEntry(30f,30f))
        barList.add(BarEntry(40f,40f))
    }
}