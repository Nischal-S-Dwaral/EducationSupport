package com.example.educationsupport

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtInputEmail: TextInputEditText
    private lateinit var txtInputPwd: TextInputEditText
    private lateinit var btnRegister: Button
    private lateinit var educatorRadioButton: RadioButton

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

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@RegisterActivity, "Please enter email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@RegisterActivity, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser

                        val map = HashMap<String, Any>()
                        if (user != null) {
                            user.email?.let { it1 -> map.put("email", it1) }
                            user.uid.let { it2 -> map.put("uid", it2)}
                            map["isEducator"] = educatorRadioButton.isChecked
                        }

                        /**
                         * Put value to firebase database
                         */
                        if (user != null) {
                            reference.child("Users").child(user.uid).setValue(map).addOnCompleteListener {
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
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@RegisterActivity, "Registration failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}