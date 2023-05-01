package com.example.educationsupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.educationsupport.fragment.learner.CourseListFragment
import com.example.educationsupport.fragment.learner.EnrolledCoursesFragment
import com.example.educationsupport.fragment.learner.HomeFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class LearnerMainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userProfileImage: CircleImageView

    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learner_main)

        /**
         * Set up the toolbar
         */
        toolbar= findViewById(R.id.homeToolbar)
        setSupportActionBar(toolbar)

        /**
         * Display application icon in the toolbar
         */
        supportActionBar!!.title = ""

        /**
         * Replacing App Bar
         */
        appBarLayout = findViewById(R.id.appBar)

        /**
         * Initialise circle image view and add on click listener
         */
        userProfileImage = findViewById(R.id.user_profile_image)
        userProfileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        /**
         * Set up the navigation view
         */
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> selectedFragment(HomeFragment())
                R.id.nav_course_list -> selectedFragment(CourseListFragment())
                R.id.nav_enrolled_courses -> selectedFragment(EnrolledCoursesFragment())
            }
            return@setOnItemSelectedListener false
        }
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    /**
     * @param fragment - the chosen fragment
     * @return The selected fragment is rendered
     */
    private fun selectedFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }
}