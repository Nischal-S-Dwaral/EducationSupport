package com.example.educationsupport.fragment.educator


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.educationsupport.R
import com.example.educationsupport.constants.CourseListConstants
import com.example.educationsupport.educator.CreateQuizActivity

class QuestionCountFragment() : DialogFragment() {

    lateinit var count: EditText
    lateinit var activityName: EditText
    lateinit var okBtn: Button
    lateinit var intent: Intent
    lateinit var courseSpinner: Spinner
    var courseSelected: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_question_count, container, false)
        courseSpinner = view.findViewById(R.id.courseSpinner)

        val courseList = CourseListConstants.getCourseList()
        var cl = ArrayList<String>()
        cl.add("Select Course")
        for (c in courseList){
           cl.add(c.courseName)
        }
        val arrayAdp = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,cl)
        courseSpinner.adapter = arrayAdp

        courseSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                courseSelected = cl[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        okBtn = view.findViewById(R.id.buttonOK)
        okBtn.setOnClickListener {
            count = view.findViewById(R.id.questionCount)
            val quesCount = count.text.toString();
            activityName = view.findViewById(R.id.activityNameEditText)
            val quizName = activityName.text.toString();

            intent = Intent(requireContext(), CreateQuizActivity::class.java);
            intent.putExtra("count", quesCount)
            intent.putExtra("quizName", quizName)
            intent.putExtra("courseName",courseSelected) //use this to link quiz to course
            startActivity(intent)
        }
        return view;
    }
}