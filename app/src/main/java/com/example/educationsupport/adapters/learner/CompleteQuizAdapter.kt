package com.example.educationsupport.adapters.learner

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
import com.example.educationsupport.model.QuizResultModel

class CompleteQuizAdapter(private val quizResultList: ArrayList<QuizResultModel>, private val context: Context) :
    RecyclerView.Adapter<CompleteQuizAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCompletedQuizName: TextView
        var progressBar: ProgressBar
        var tvCompletedQuizScoreViewProgress: TextView

        init {
            tvCompletedQuizName = itemView.findViewById(R.id.tv_completed_quiz_name)
            progressBar = itemView.findViewById(R.id.quiz_score_progress_bar)
            tvCompletedQuizScoreViewProgress = itemView.findViewById(R.id.completed_quiz_score_text_view_progress)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.completed_quiz_score_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quizResult = quizResultList[position]
        val countCorrectAnswer = quizResult.correctAnswerScore ?: 0
        val countTotalQuestion = quizResult.totalQuestionScore ?: 0

        val quizScore = countCorrectAnswer * 100/countTotalQuestion

        holder.tvCompletedQuizName.text = quizResult.quizName
        holder.progressBar.progress = quizScore
        holder.tvCompletedQuizScoreViewProgress.text = quizScore.toString()+"%"

        holder.itemView.setOnClickListener {
            //TODO Send data through the intent
            val intent = Intent(context, QuizAnswerReviewActivity::class.java)
            intent.putExtra(Constants.QUIZ_RESULT_ID, quizResult.id)
            context.startActivity(intent)
        }
    }
}