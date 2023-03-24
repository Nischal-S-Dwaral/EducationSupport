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


private const val VIEW_TYPE_QUIZ_COMPLETED = 0
private const val VIEW_TYPE_START_QUIZ = 1

class QuizListCardAdapter(private val quizList: ArrayList<Quiz>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class QuizCompletedScoreCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvCompletedQuizName: TextView
        private var progressBar: ProgressBar
        private var tvCompletedQuizScoreViewProgress: TextView

        init {
            tvCompletedQuizName = itemView.findViewById(R.id.tv_completed_quiz_name)
            progressBar = itemView.findViewById(R.id.quiz_score_progress_bar)
            tvCompletedQuizScoreViewProgress = itemView.findViewById(R.id.completed_quiz_score_text_view_progress)
        }
        fun bind(quiz: Quiz) {
            tvCompletedQuizName.text = quiz.title
            progressBar.progress = quiz.score
            tvCompletedQuizScoreViewProgress.text = quiz.score.toString()+"%"

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Quiz Completed View Holder is clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class StartQuizCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvQuizName: TextView

        init {
            tvQuizName = itemView.findViewById(R.id.tv_quiz_name)
        }

        fun bind(quiz: Quiz) {
            tvQuizName.text = quiz.title

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Start Quiz View Holder is clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_QUIZ_COMPLETED -> QuizCompletedScoreCardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.completed_quiz_score_card, parent, false))
            VIEW_TYPE_START_QUIZ -> StartQuizCardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.start_quiz_card, parent, false))
            else -> throw java.lang.IllegalArgumentException("Unknown view type")
        }
    }

    /**
     * Based on the conditions get the view type
     */
    override fun getItemViewType(position: Int): Int {
        return if (quizList[position].isCompleted) {
            VIEW_TYPE_QUIZ_COMPLETED
        } else {
            VIEW_TYPE_START_QUIZ
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_QUIZ_COMPLETED) {
            (holder as QuizCompletedScoreCardViewHolder).bind(quizList[position])
        } else {
            (holder as StartQuizCardViewHolder).bind(quizList[position])
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}