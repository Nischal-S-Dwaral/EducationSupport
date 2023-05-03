package com.example.educationsupport.fragment.educator


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.EducatorCourseListCardAdapter
import com.example.educationsupport.constants.CourseListConstants
import com.example.educationsupport.educator.CreateQuizActivity
import com.example.educationsupport.model.Course
import com.example.educationsupport.model.EnrolledCourse
import com.example.educationsupport.model.Quiz
import com.example.educationsupport.model.QuizModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class QuestionCountFragment() : DialogFragment() {

    lateinit var count: EditText
    lateinit var activityName: EditText
    lateinit var okBtn: Button
    lateinit var intent: Intent
    lateinit var courseSpinner: Spinner
    var courseSelected: String = ""
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_question_count, container, false)
        courseSpinner = view.findViewById(R.id.courseSpinner)
        currentUser = FirebaseAuth.getInstance().currentUser!!


        var courseList = ArrayList<String>()
        courseList.add("Select Course")


        databaseReference = FirebaseDatabase.getInstance().reference.child("Courses")
        databaseReference.orderByChild("educatorId").equalTo(currentUser.uid)

        databaseReference.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(course in snapshot.children){
                        val courseData = course.getValue(QuizModel::class.java)
                        courseList.add(courseData!!.name!!)

                    }

                    val arrayAdp = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,courseList)
                    courseSpinner.adapter = arrayAdp

                    courseSpinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {
                            courseSelected = courseList[position].toString()
                            print(courseList)

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
                        val quizName = " "+activityName.text.toString();

                        intent = Intent(requireContext(), CreateQuizActivity::class.java);
                        intent.putExtra("count", quesCount)
                        intent.putExtra("quizName", quizName)
                        intent.putExtra("courseName",courseSelected) //use this to link quiz to course
                        startActivity(intent)
                    }


                }
                }




            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }
        })


        return view;
    }
}