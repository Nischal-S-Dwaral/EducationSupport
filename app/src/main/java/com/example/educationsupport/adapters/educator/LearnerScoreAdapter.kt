package com.example.educationsupport.adapters.educator

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.learner.QuizAnswerReviewActivity
import com.example.educationsupport.model.LearnerScore

class LearnerScoreAdapter(private val learnerScoreList: ArrayList<LearnerScore>, private val context: Context) :
    RecyclerView.Adapter<LearnerScoreAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLearnerName: TextView
        var progressBar: ProgressBar
        var tvCompletedQuizScoreViewProgress: TextView

        init {
            tvLearnerName = itemView.findViewById(R.id.tv_learner_name)
            progressBar = itemView.findViewById(R.id.quiz_score_progress_bar)
            tvCompletedQuizScoreViewProgress = itemView.findViewById(R.id.learner_quiz_score)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.learner_quiz_score_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return learnerScoreList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val learner = learnerScoreList[position]

        holder.tvLearnerName.text = learner.learnerName

        holder.progressBar.progress = learner.scorePercentage
        holder.tvCompletedQuizScoreViewProgress.text = learner.scorePercentage.toString()+"%"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, QuizAnswerReviewActivity::class.java)
            intent.putExtra(Constants.QUIZ_RESULT_ID, learner.quizResultId)
            context.startActivity(intent)
        }
    }
}