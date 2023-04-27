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
//import com.example.educationsupport.educator.ViewQuizActivity
import com.example.educationsupport.educator.ViewQuizAndScore
import com.example.educationsupport.model.Quiz

class QuizListAdapter(private val quizList: List<Quiz>?, private val context: Context) :
    RecyclerView.Adapter<QuizListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuizName: TextView
        var progressLoadQuiz: ProgressBar

        init {
            tvQuizName = itemView.findViewById(R.id.tv_quiz_name)
            progressLoadQuiz = itemView.findViewById(R.id.progress_load_quiz)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvQuizName.text = quizList!![position].title

        holder.itemView.setOnClickListener {
           holder.progressLoadQuiz.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                holder.progressLoadQuiz.visibility = View.GONE
                //val intent = Intent(context, ViewQuizActivity::class.java)
                val intent = Intent(context, ViewQuizAndScore::class.java)
                context.startActivity(intent)
            }, 2000)
        }
    }

    override fun getItemCount(): Int {
        return quizList!!.size
    }
}