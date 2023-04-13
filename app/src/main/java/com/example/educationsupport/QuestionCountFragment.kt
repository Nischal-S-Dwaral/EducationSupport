package com.example.educationsupport


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.learner.QuizResultActivity

class QuestionCountFragment : DialogFragment() {

    lateinit var count: EditText
    lateinit var activityName: EditText
    lateinit var okBtn: Button
    lateinit var intent: Intent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_question_count, container, false)
        okBtn = view.findViewById(R.id.buttonOK)
        okBtn.setOnClickListener {
            count = view.findViewById(R.id.questionCount)
            val quesCount = count.text.toString();
            activityName = view.findViewById(R.id.activityNameEditText)
            val quizName = activityName.text.toString();

            intent = Intent(requireContext(), CreateQuizActivity::class.java);
            intent.putExtra("count", quesCount)
            intent.putExtra("quizName", quizName)

            startActivity(intent)
        }
        return view;
    }
}