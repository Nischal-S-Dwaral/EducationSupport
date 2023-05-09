package com.example.educationsupport

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var txtInputEmail: TextInputEditText
    private lateinit var txtInputPwd: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    /**
     * For Firebase authentication
     */
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(
                this@LoginActivity, "User Logged In.",
                Toast.LENGTH_SHORT
            ).show()
            startActivityBasedOnUser(currentUser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**
         * Initialising a notification channel
         */
        createNotificationChannel()

        /**
         * Initialise
         */
        txtInputEmail = findViewById(R.id.login_email_edit_text)
        txtInputPwd = findViewById(R.id.login_password_edit_text)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register_login_activity)

        /**
         * Initialise Firebase authentication
         */
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().reference

        /**
         * Remove the time and battery etc top bar
         */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * OnClick of Register Button, move to Register Activity
         */
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        /**
         * OnClick of Login Button
         */
        btnLogin.setOnClickListener {
            val email: String = txtInputEmail.text.toString()
            val password: String = txtInputPwd.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@LoginActivity, "Please enter email address", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Please enter password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(
                            this@LoginActivity, "Login Successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val user = auth.currentUser

                        /**
                         *  Sign in success, update UI with the signed-in with main activity
                         */
                        if (user != null) {
                            startActivityBasedOnUser(user)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@LoginActivity, "Login failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    /**
     * Check if the user is Educator or Learner
     */
    private fun startActivityBasedOnUser(user: FirebaseUser) {

        val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(user.uid)
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val educator = snapshot.child("educator").value as Boolean
                    if (educator) {
                        val intent = Intent(this@LoginActivity, EducatorMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@LoginActivity, LearnerMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Creating Notification Channel for the application
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "My Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notification_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}