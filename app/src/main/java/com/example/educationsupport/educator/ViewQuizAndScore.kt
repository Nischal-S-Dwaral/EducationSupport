package com.example.educationsupport.educator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerScoreAdapter
import com.example.educationsupport.model.LearnerScore
import com.example.educationsupport.model.QuizResultModel
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewQuizAndScore : AppCompatActivity() {

    private var quizId: String? = null
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

        /**
         * Setup the start quiz list recycler view
        */
        learnerScoreLayoutManager = LinearLayoutManager(this@ViewQuizAndScore)
        learnerScoreRecyclerView = findViewById(R.id.view_quiz_learner_score)
        learnerScoreRecyclerView.setHasFixedSize(true)
        learnerScoreRecyclerView.layoutManager = learnerScoreLayoutManager

        quizId = intent.getStringExtra("quizId").toString()
        getQuizResultData(quizId)
    }

    private fun getQuizResultData(quizId: String?) {
        if (quizId != null) {
            FirebaseDatabase.getInstance().getReference("QuizResult").orderByChild("quizId").equalTo(quizId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val learnerScoreList = ArrayList<LearnerScore>()
                        if (snapshot.exists()) {
                            for (data in snapshot.children) {
                                val quizResult = data.getValue(QuizResultModel::class.java)
                                if(quizResult != null) {
                                    supportActionBar!!.title = quizResult.quizName

                                    if (quizResult.learnerId != null) {
                                        val learnerScore = LearnerScore(
                                            quizResult.learnerName,
                                            quizResult.scorePercentage!!,
                                            quizResult.id
                                        )
                                        learnerScoreList.add(learnerScore)
                                    }
                                }
                            }
                            learnerScoreAdapter = LearnerScoreAdapter(learnerScoreList, this@ViewQuizAndScore)
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