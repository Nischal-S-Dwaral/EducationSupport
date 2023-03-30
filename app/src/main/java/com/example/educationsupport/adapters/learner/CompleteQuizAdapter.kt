package com.example.educationsupport.adapters.learner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.model.Quiz

class CompleteQuizAdapter(private val quizList: List<Quiz>, private val context: Context) :
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
        return quizList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quiz = quizList[position]

        holder.tvCompletedQuizName.text = quiz.title
        holder.progressBar.progress = quiz.score
        holder.tvCompletedQuizScoreViewProgress.text = quiz.score.toString()+"%"

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Quiz Completed View Holder is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}