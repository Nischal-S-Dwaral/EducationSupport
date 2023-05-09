package com.example.educationsupport.fragment.educator


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.educator.CreateQuizActivity
import com.example.educationsupport.model.Course
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class QuestionCountFragment() : DialogFragment() {

    lateinit var count: EditText
    lateinit var activityName: EditText
    lateinit var okBtn: Button
    lateinit var intent: Intent
    lateinit var courseSpinner: Spinner
    var courseSelected: String = ""
    var courseIdSelected: String = ""
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    private lateinit var scheduleQuiz: RadioButton
    private lateinit var startDatePicker: DatePicker
    private lateinit var endDatePicker: DatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_question_count, container, false)
        courseSpinner = view.findViewById(R.id.courseSpinner)
        currentUser = FirebaseAuth.getInstance().currentUser!!
        okBtn = view.findViewById(R.id.buttonOK)
        count = view.findViewById(R.id.questionCount)
        activityName = view.findViewById(R.id.activityNameEditText)
        scheduleQuiz = view.findViewById(R.id.add_start_and_end_date_quiz_radio_btn)
        startDatePicker = view.findViewById(R.id.start_date_quiz)
        endDatePicker = view.findViewById(R.id.end_date_quiz)


        val courseNameList = ArrayList<String>()
        val courseIdList = ArrayList<String>()
        courseNameList.add("Select Course")

        //Fetching Course Names created by current Educator
        databaseReference = FirebaseDatabase.getInstance().reference.child("Courses")
        databaseReference.orderByChild("educatorId").equalTo(currentUser.uid)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(course in snapshot.children){
                        val courseData = course.getValue(Course::class.java)
                        courseNameList.add(courseData!!.name!!)
                        courseIdList.add(courseData.id!!)
                    }
                }
                }
           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(view.context, error.message, Toast.LENGTH_SHORT).show()
           }
        })

        //Setting course name list to dropdown
        val arrayAdp = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,courseNameList)
        courseSpinner.adapter = arrayAdp
        courseSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                courseSelected = courseNameList[position]
                courseIdSelected = if (position > 0) {
                    courseIdList[position - 1]
                } else {
                    courseIdList[0]
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(view.context, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }

        okBtn.setOnClickListener {
            val flag = validateFields(view)
            if(flag){
                val quizName = " "+activityName.text.toString()
                val quesCount = count.text.toString()

                if (scheduleQuiz.isChecked) {
                    val startDay: Int = startDatePicker.dayOfMonth
                    val startMonth: Int = startDatePicker.month + 1
                    val startYear: Int = startDatePicker.year
                    val startDate = "$startDay/$startMonth/$startYear"

                    val endDay: Int = endDatePicker.dayOfMonth
                    val endMonth: Int = endDatePicker.month + 1
                    val endYear: Int = endDatePicker.year
                    val endDate = "$endDay/$endMonth/$endYear"

                    intent = Intent(requireContext(), CreateQuizActivity::class.java)
                    intent.putExtra("count", quesCount)
                    intent.putExtra("quizName", quizName)
                    intent.putExtra("courseId", courseIdSelected) //use this to link quiz to course
                    intent.putExtra(Constants.IS_QUIZ_SCHEDULED, true)
                    intent.putExtra(Constants.QUIZ_START_DATE, startDate)
                    intent.putExtra(Constants.QUIZ_END_DATE, endDate)
                    startActivity(intent)
                } else {
                    intent = Intent(requireContext(), CreateQuizActivity::class.java)
                    intent.putExtra("count", quesCount)
                    intent.putExtra("quizName", quizName)
                    intent.putExtra("courseId", courseIdSelected) //use this to link quiz to course
                    intent.putExtra(Constants.IS_QUIZ_SCHEDULED, false)
                    startActivity(intent)
                }
            }
        }

        return view
    }

    private fun validateFields(view: View): Boolean {
        var flag = true
        if(activityName.text.toString().isEmpty()){
            activityName.error = "Name is missing"
            flag = false
        }
        if(count.text.toString().isEmpty()){
            count.error = "Question count missing"
            flag = false
        }
        if(courseSelected.isEmpty() || courseSelected.equals("Select Course")) {
            Toast.makeText(view.context, "Select valid course", Toast.LENGTH_SHORT).show()
            flag = false
        }
        return flag
    }
}