package com.example.educationsupport.educator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.educator.QuizListAdapter
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.fragment.educator.QuestionCountFragment
import com.example.educationsupport.model.QuizModel
import com.google.firebase.database.*

class EducatorCourseActivity : AppCompatActivity() {

    private var courseId: String? = null
    private var courseName: String? = null
    private var courseDesc: String? = null
    private lateinit var toolbar: Toolbar

    private lateinit var tvDescription: TextView

    private lateinit var quizListRecyclerView: RecyclerView
    private lateinit var quizListLayoutManager: LinearLayoutManager
    private lateinit var quizListAdapter: QuizListAdapter

    private lateinit var databaseReference: DatabaseReference
   private lateinit var addQuizBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educator_view_course)

        /**
         * Take value passed from the Intent
         */
        courseId = intent.getStringExtra(Constants.COURSE_ID).toString()
        courseName = intent.getStringExtra("courseName").toString()
        courseDesc = intent.getStringExtra("courseDesc").toString()

        addQuizBtn = findViewById(R.id.addQuizButton);

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Set the toolbar
         */
        toolbar = findViewById(R.id.view_course_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = courseName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /**
         * Set the description
         */
        tvDescription = findViewById(R.id.tv_view_course_description)
        tvDescription.text = courseDesc



        /**
         * Get the QUIZ list of the course
         */
        databaseReference = FirebaseDatabase.getInstance().reference.child("Quiz")
        databaseReference.orderByChild("courseId").equalTo(courseId)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val quizList = mutableListOf <QuizModel>()
                    for(data in snapshot.children){
                        val quiz = data.getValue(QuizModel::class.java)
                        if(quiz!=null){
                        quizList.add(quiz)
                        }
                   }
                    /**
                     * Setup the start quiz list recycler view
                     */
                    quizListLayoutManager = LinearLayoutManager(this@EducatorCourseActivity)
                    quizListAdapter = QuizListAdapter(quizList, this@EducatorCourseActivity)
                    quizListRecyclerView = findViewById(R.id.rv_view_quiz_list)
                    quizListRecyclerView.setHasFixedSize(true)
                    quizListRecyclerView.layoutManager = quizListLayoutManager
                    quizListRecyclerView.adapter = quizListAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }
        })



        addQuizBtn.setOnClickListener {
            val showPopUp = QuestionCountFragment()
            showPopUp.show(supportFragmentManager,"showPopUp")
        }

        }

    /**
     * Handle the event of back button pressed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}