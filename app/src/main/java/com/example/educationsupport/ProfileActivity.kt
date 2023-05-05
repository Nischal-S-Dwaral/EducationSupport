package com.example.educationsupport

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.educationsupport.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ProfileActivity : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var displayName: TextView
    private lateinit var bio: TextView
    private lateinit var editButton: Button
    private lateinit var logoutButton: Button
    private lateinit var imageEditButton: Button
    private var isEditing = false

    private lateinit var databaseReference: DatabaseReference //for adding profile
    var currentEducatorUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var imageURI: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        currentEducatorUser = auth.currentUser

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
        imageEditButton = findViewById(R.id.imageEditButton)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(currentEducatorUser!!.uid)

        getProfileValues()

        //For profile image
        var getContentForImg = registerForActivityResult(
            GetContent()
        ) {
            imageURI = it!!.normalizeScheme()
            if(imageURI!=null){
                profilePicture.setImageURI(imageURI)
            }
        }

        /**
         * Set the imageView click listener
         */
        imageEditButton.setOnClickListener{

            getContentForImg.launch("image/*");

            enableEditing()
        }
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

    private fun getProfileValues() {
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                   val tmpUser = snapshot.getValue(Users::class.java)
                    if(tmpUser!=null) {
                        displayName.text = tmpUser.username ?: "Edit to set Username"
                        bio.text = tmpUser.bio ?: "Edit to set bio"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        storageReference = FirebaseStorage.getInstance().getReference("Users").child("profileImage").child("profileImage"+currentEducatorUser!!.uid)

        storageReference.getBytes(10 * 1080 * 1080).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            profilePicture.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this@ProfileActivity, "Image not fetched",Toast.LENGTH_SHORT)
        }

  }

    private fun enableEditing() {
        // Set the TextViews to be editable
        displayName.isFocusableInTouchMode = true
        bio.isFocusableInTouchMode = true
        profilePicture.isEnabled = true

        // Change the edit button text
        editButton.text = "SAVE CHANGES"

        // Set the editing flag
        isEditing = true
    }

    private fun disableEditing() {
        // Set the TextViews to be non-editable
        displayName.isFocusable = false
        bio.isFocusable = false
        profilePicture.isEnabled = false
        // Change the edit button text
        editButton.text = "EDIT PROFILE"

        // Set the editing flag
        isEditing = false
    }

    private fun saveEditedValues() {
        // Save the edited values to a database or server
        val newDisplayName = displayName.text.toString()
        val newBio = bio.text.toString()


        databaseReference.child("username").setValue(newDisplayName).addOnCompleteListener{
                Toast.makeText(
                    this@ProfileActivity,
                    "Name saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
        }
        databaseReference.child("bio").setValue(newBio).addOnCompleteListener{
                Toast.makeText(
                    this@ProfileActivity,
                    "Bio saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
        }

            uploadProfileImage()
         // Update the TextViews with the new values
            displayName.text = newDisplayName
            bio.text = newBio
    }

    private fun uploadProfileImage() {

        val filename = "profileImage"+currentEducatorUser!!.uid
        storageReference = FirebaseStorage.getInstance().getReference("Users").child("profileImage/$filename")
        storageReference.putFile(imageURI).addOnSuccessListener {
            Toast.makeText(
                this@ProfileActivity,
                "Image saved successfully",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                this@ProfileActivity,
                "Image not saved ",
                Toast.LENGTH_SHORT
            ).show()
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