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
import com.example.educationsupport.model.AnsweredQuestion
import com.example.educationsupport.model.QuestionModel

class QuizQuestionAdapter(
    private val quizQuestionList: List<QuestionModel>,
    private val context: Context
) : RecyclerView.Adapter<QuizQuestionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuestionText: TextView
        var tvOption1: TextView
        var tvOption2: TextView
        var tvOption3: TextView
        var tvOption4: TextView
        var tvCorrectAnswer: TextView

        init {
            tvQuestionText = itemView.findViewById(R.id.tv_question_text)
            tvOption1 = itemView.findViewById(R.id.tv_option1_text)
            tvOption2 = itemView.findViewById(R.id.tv_option2_text)
            tvOption3 = itemView.findViewById(R.id.tv_option3_text)
            tvOption4 = itemView.findViewById(R.id.tv_option4_text)
            tvCorrectAnswer = itemView.findViewById(R.id.tv_correct_answer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.quiz_question_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.tvQuestionText.text = quizQuestionList!![position].question
        holder.tvOption1.text = quizQuestionList!![position].optionOne
        holder.tvOption2.text = quizQuestionList!![position].optionTwo
        holder.tvOption3.text = quizQuestionList!![position].optionThree
        holder.tvOption4.text = quizQuestionList!![position].optionFour
        holder.tvCorrectAnswer.text = quizQuestionList!![position].correctAnswer.toString()

    }



    override fun getItemCount(): Int {
        return quizQuestionList!!.size
    }
}