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
//import com.example.educationsupport.learner.QuizAnswerReviewActivity
import com.example.educationsupport.model.Learner

class LearnerScoreAdapter(private val learnerList: List<Learner>, private val context: Context) :
    RecyclerView.Adapter<LearnerScoreAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLearnerName: TextView
        //var progressBar: ProgressBar
        //var tvCompletedQuizScoreViewProgress: TextView
        var learnerQuizScore: TextView
        //var scoreTitle: TextView

        init {
            tvLearnerName = itemView.findViewById(R.id.tv_learner_name)
            learnerQuizScore = itemView.findViewById(R.id.learner_quiz_score)
            //scoreTitle = itemView.findViewById(R.id.score_title)
            //progressBar = itemView.findViewById(R.id.quiz_score_progress_bar)
            //tvCompletedQuizScoreViewProgress = itemView.findViewById(R.id.completed_quiz_score_text_view_progress)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.learner_quiz_score_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return learnerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val learner = learnerList[position]

        holder.tvLearnerName.text = learner.name

        val correctAnswers = learner.correctAnswer
        val totalQuestions = learner.totalQuestion

        val resultString = buildString {
            append(correctAnswers)
            append(" out of ")
            append(totalQuestions)
        }

        holder.learnerQuizScore.text = resultString
        //holder.scoreTitle.text = "Learners Score"
        //holder.progressBar.progress = learner.score
        //holder.tvCompletedQuizScoreViewProgress.text = learner.score.toString()+"%"

        holder.itemView.setOnClickListener {
            //TODO Send data through the intent
//            val intent = Intent(context, QuizAnswerReviewActivity::class.java)
//            context.startActivity(intent)
        }
    }
}