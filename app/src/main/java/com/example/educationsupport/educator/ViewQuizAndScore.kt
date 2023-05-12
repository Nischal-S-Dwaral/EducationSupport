package com.example.educationsupport.educator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.LearnerScoreAdapter
import com.example.educationsupport.model.LearnerScore
import com.example.educationsupport.model.QuizResultModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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

    private lateinit var chart: BarChart
    private lateinit var viewQuestionBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quiz_and_score)

        toolbar = findViewById(R.id.view_quiz_and_score_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Initialise bar chart
         */
        chart = findViewById(R.id.view_quiz_and_score_bar_chart)
        chart.description.isEnabled = false

        /**
         * Initialise the view score list recycler view
        */
        learnerScoreLayoutManager = LinearLayoutManager(this@ViewQuizAndScore)
        learnerScoreRecyclerView = findViewById(R.id.view_quiz_learner_score)
        learnerScoreRecyclerView.setHasFixedSize(true)
        learnerScoreRecyclerView.layoutManager = learnerScoreLayoutManager

        quizId = intent.getStringExtra("quizId").toString()
        getQuizResultData(quizId)

        //Button to view quiz questions
        viewQuestionBtn = findViewById(R.id.viewQuestionBtn)
        viewQuestionBtn.setOnClickListener{
            val intent = Intent(this@ViewQuizAndScore, ViewQuizActivity::class.java)
            intent.putExtra("quizId",quizId);
            startActivity(intent)
        }

    }

    private fun getQuizResultData(quizId: String?) {
        if (quizId != null) {
            FirebaseDatabase.getInstance().getReference("QuizResult").orderByChild("quizId").equalTo(quizId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val quizScorePercentageList = ArrayList<Int>()
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

                                        quizScorePercentageList.add(quizResult.scorePercentage)
                                    }
                                }
                            }
                            learnerScoreAdapter = LearnerScoreAdapter(learnerScoreList, this@ViewQuizAndScore)
                            learnerScoreRecyclerView.adapter = learnerScoreAdapter

                            getBarChartDisplay(quizScorePercentageList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@ViewQuizAndScore, error.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun getBarChartDisplay(quizScorePercentageList: ArrayList<Int>) {

        val range1 = mutableListOf<Int>()
        val range2 = mutableListOf<Int>()
        val range3 = mutableListOf<Int>()
        val range4 = mutableListOf<Int>()

        for (num in quizScorePercentageList) {
            when (num) {
                in 0..20 -> range1.add(num)
                in 21..40 -> range2.add(num)
                in 61..80 -> range3.add(num)
                in 81..100 -> range4.add(num)
            }
        }

        val entries = mutableListOf<BarEntry>()
        entries.add(BarEntry(0f, range1.size.toFloat()))
        entries.add(BarEntry(1f, range2.size.toFloat()))
        entries.add(BarEntry(2f, range3.size.toFloat()))
        entries.add(BarEntry(3f, range4.size.toFloat()))

        val dataSet = BarDataSet(entries, "Score")
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 15f

        val data = BarData(dataSet)

        chart.data = data

        val labels = mutableListOf<String>()
        labels.add("0-20")
        labels.add("20-40")
        labels.add("60-80")
        labels.add("80-100")

        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)

        // Show only top and bottom axis values
        chart.axisLeft.setDrawLabels(true)
        chart.axisLeft.setDrawTopYLabelEntry(true)
        chart.axisRight.setDrawLabels(false)
        chart.axisRight.setDrawTopYLabelEntry(true)
        chart.xAxis.setDrawLabels(true)

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.invalidate() // Refresh the chart
    }

    /**
     * Handle the event of back button pressed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}