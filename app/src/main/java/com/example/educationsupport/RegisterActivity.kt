package com.example.educationsupport

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.educationsupport.model.Users
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtInputEmail: TextInputEditText
    private lateinit var txtInputPwd: TextInputEditText
    private lateinit var btnRegister: Button
    private lateinit var educatorRadioButton: RadioButton
    private lateinit var txtInputUsername: TextInputEditText
    /**
     * For Firebase authentication
     */
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /**
         * Initialise
         */
        txtInputEmail = findViewById(R.id.register_email_edit_text)
        txtInputPwd = findViewById(R.id.register_password_edit_text)
        btnRegister = findViewById(R.id.btn_register)
        educatorRadioButton = findViewById(R.id.register_educator_radio_btn)
        txtInputUsername = findViewById(R.id.register_username_edit_text)

        /**
         * Initialise Firebase authentication
         */
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().reference

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        btnRegister.setOnClickListener {
            val email: String = txtInputEmail.text.toString()
            val password: String = txtInputPwd.text.toString()
            val username: String = txtInputUsername.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@RegisterActivity, "Please enter email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@RegisterActivity, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this@RegisterActivity, "Please enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser

                        // Set display name for the user
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()

                        //Update the display name for the user
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    // User profile updated successfully
                                    val userDataObject = Users(
                                        user.email,
                                        educatorRadioButton.isChecked,
                                        user.uid,
                                        username
                                    )

                                    /**
                                     * Put value to firebase database
                                     */
                                    reference.child("Users").child(user.uid).setValue(userDataObject).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            /**
                                             * Move to Login Activity
                                             */
                                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                }
                            }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@RegisterActivity, "Registration failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}