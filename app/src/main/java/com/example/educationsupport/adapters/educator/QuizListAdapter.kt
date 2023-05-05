package com.example.educationsupport.adapters.educator

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.educator.ViewQuizAndScore
import com.example.educationsupport.model.QuizModel

class QuizListAdapter(private val quizList: List<QuizModel>?, private val context: Context) :
    RecyclerView.Adapter<QuizListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuizName: TextView
        var progressLoadQuiz: ProgressBar
        var tvStartDate: TextView
        var tvEndDate: TextView

        init {
            tvQuizName = itemView.findViewById(R.id.tv_quiz_name)
            progressLoadQuiz = itemView.findViewById(R.id.progress_load_quiz)
            tvStartDate = itemView.findViewById(R.id.tv_quiz_start_date)
            tvEndDate = itemView.findViewById(R.id.tv_quiz_end_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizList!![position]
        holder.tvQuizName.text = quiz.name

        if (quiz.scheduledQuiz) {
            holder.tvStartDate.visibility = View.VISIBLE
            holder.tvStartDate.text = "Start Date: ${quiz.startDate}"

            holder.tvEndDate.visibility = View.VISIBLE
            holder.tvEndDate.text = "End Date: ${quiz.endDate}"
        }

        holder.itemView.setOnClickListener {
            holder.progressLoadQuiz.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                holder.progressLoadQuiz.visibility = View.GONE
                val intent = Intent(context, ViewQuizAndScore::class.java)
                intent.putExtra("quizId",quiz.id)
                intent.putExtra("quizName",quiz.name)
                context.startActivity(intent)
            }, 2000)
        }
    }

    override fun getItemCount(): Int {
        return quizList!!.size
    }
}