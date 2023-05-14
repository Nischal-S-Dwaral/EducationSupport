package com.example.educationsupport.fragment.educator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.educationsupport.EducatorMainActivity
import com.example.educationsupport.R
import com.example.educationsupport.model.Course
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddNewCourseFragment : Fragment() {

    private lateinit var txtInputCourseName: TextInputEditText
    private lateinit var txtInputCourseDescription: TextInputEditText
    private lateinit var btnSubmit: Button

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.educator_add_course, container, false)

        /**
         * Initialise
         */
        txtInputCourseName = view.findViewById(R.id.add_course_name_edit_text)
        txtInputCourseDescription = view.findViewById(R.id.add_course_description_edit_text)
        btnSubmit = view.findViewById(R.id.btn_submit_add_course)

        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        auth = FirebaseAuth.getInstance()

        val currentEducatorUser = auth.currentUser

        btnSubmit.setOnClickListener {
            if (currentEducatorUser != null) {
                saveCourseData(currentEducatorUser.uid, view)
            }
            val intent = Intent(context, EducatorMainActivity::class.java)
            context?.startActivity(intent)
        }
        return view
    }

    private fun saveCourseData(educatorUid: String, view: View) {
        val courseName = txtInputCourseName.text.toString()
        val courseDescription = txtInputCourseDescription.text.toString()

        if (courseName.isEmpty() || courseDescription.isEmpty()) {
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Please enter all fields!!")
                .setCancelable(true)
                .setPositiveButton("OK") { dialog, id ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
        } else {
            val courseId = databaseReference.push().key!!
            val course = Course(courseId, courseName, courseDescription, educatorUid)

            databaseReference.child(courseId).setValue(course)
                .addOnSuccessListener {
                    Toast.makeText(view.context, "Added Course Successfully!!", Toast.LENGTH_SHORT)
                        .show()

                    txtInputCourseName.text?.clear()
                    txtInputCourseDescription.text?.clear()

                }.addOnFailureListener {
                    Toast.makeText(view.context, "Error!! ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}