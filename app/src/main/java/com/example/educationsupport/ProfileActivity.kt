package com.example.educationsupport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var displayName: TextView
    private lateinit var bio: TextView
    private lateinit var editButton: Button
    private lateinit var logoutButton: Button

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /**
         * Set the Toolbar as the action bar
         */
        val toolbar: Toolbar = findViewById(R.id.profile_toolbar)
        setSupportActionBar(toolbar)

        /**
         * Enable the back button in the action bar
         */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /**
         * Initialise
         */
        profilePicture = findViewById(R.id.profile_picture)
        displayName = findViewById(R.id.profile_display_name)
        bio = findViewById(R.id.profile_bio)
        editButton = findViewById(R.id.profile_edit_button)
        logoutButton = findViewById(R.id.profile_logout_button)

        /**
         * Set the edit button click listener
         */
        editButton.setOnClickListener {
            if (isEditing) {
                /**
                 * Save the edited values and disable editing
                 */
                saveEditedValues()
                disableEditing()
            } else {
                /**
                 *  Enable editing
                 */
                enableEditing()
            }
        }

        /**
         * Logout functionality
         */
        logoutButton.setOnClickListener {
            /**
             *  Sign out the user and navigate back to the login activity
             */
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun enableEditing() {
        // Set the TextViews to be editable
        displayName.isFocusableInTouchMode = true
        bio.isFocusableInTouchMode = true

        // Change the edit button text
        editButton.text = "SAVE CHANGES"

        // Set the editing flag
        isEditing = true
    }

    private fun disableEditing() {
        // Set the TextViews to be non-editable
        displayName.isFocusable = false
        bio.isFocusable = false

        // Change the edit button text
        editButton.text = "EDIT PROFILE"

        // Set the editing flag
        isEditing = false
    }

    private fun saveEditedValues() {
        // Save the edited values to a database or server
        val newDisplayName = displayName.text.toString()
        val newBio = bio.text.toString()

        // Update the TextViews with the new values
        displayName.text = newDisplayName
        bio.text = newBio
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