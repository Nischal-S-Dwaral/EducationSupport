package com.example.educationsupport.adapters.learner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.model.AnsweredQuestion

private const val VIEW_TYPE_WRONG_ANSWER = 0
private const val VIEW_TYPE_CORRECT_ANSWER = 1

class ReviewAnswerAdapter(
    private val answeredQuestionList: List<AnsweredQuestion>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Adapter for correct answer card
     */
    class CorrectAnswerCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvQuestionNumber: TextView
        private var tvQuestion: TextView
        private var tvCorrectAnswer: TextView

        init {
            tvQuestionNumber = itemView.findViewById(R.id.tv_correct_answer_card_question_number)
            tvQuestion = itemView.findViewById(R.id.tv_correct_answer_card_question_text)
            tvCorrectAnswer = itemView.findViewById(R.id.tv_correct_answer_card_answer_chosen)
        }

        fun bind(answeredQuestion: AnsweredQuestion) {
            tvQuestionNumber.text = answeredQuestion.questionNumber.toString()
            tvQuestion.text = answeredQuestion.question
            tvCorrectAnswer.text = answeredQuestion.correctAnswer
        }
    }

    /**
     * Adapter for wrong answer card
     */
    class WrongAnswerCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvQuestionNumber: TextView
        private var tvQuestion: TextView
        private var tvChosenAnswer: TextView
        private var tvCorrectAnswer: TextView

        init {
            tvQuestionNumber = itemView.findViewById(R.id.tv_wrong_answer_card_question_number)
            tvQuestion = itemView.findViewById(R.id.tv_wrong_answer_card_question_text)
            tvChosenAnswer = itemView.findViewById(R.id.tv_wrong_answer_card_answer_chosen)
            tvCorrectAnswer = itemView.findViewById(R.id.tv_wrong_answer_card_correct_answer)
        }

        fun bind(answeredQuestion: AnsweredQuestion) {
            tvQuestionNumber.text = answeredQuestion.questionNumber.toString()
            tvQuestion.text = answeredQuestion.question
            tvChosenAnswer.text = answeredQuestion.selectedAnswer
            tvCorrectAnswer.text = answeredQuestion.correctAnswer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_CORRECT_ANSWER -> CorrectAnswerCardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.correct_answer_card, parent, false))
            VIEW_TYPE_WRONG_ANSWER -> WrongAnswerCardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.wrong_answer_card, parent, false))
            else -> throw java.lang.IllegalArgumentException("Unknown view type")
        }
    }

    /**
     * Based on the conditions get the view type
     */
    override fun getItemViewType(position: Int): Int {
        return if (answeredQuestionList[position].isCorrect) {
            VIEW_TYPE_CORRECT_ANSWER
        } else {
            VIEW_TYPE_WRONG_ANSWER
        }
    }

    override fun getItemCount(): Int {
        return answeredQuestionList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_CORRECT_ANSWER) {
            (holder as CorrectAnswerCardViewHolder).bind(answeredQuestionList[position])
        } else {
            (holder as WrongAnswerCardViewHolder).bind(answeredQuestionList[position])
        }
    }
}