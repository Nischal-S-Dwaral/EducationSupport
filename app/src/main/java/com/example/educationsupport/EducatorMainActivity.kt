package com.example.educationsupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.educationsupport.fragment.educator.AddNewCourseFragment
import com.example.educationsupport.fragment.educator.AddNewLearnerFragment
import com.example.educationsupport.fragment.educator.EducatorHomeFragment
import com.example.educationsupport.fragment.learner.CourseListFragment
import com.example.educationsupport.fragment.learner.EnrolledCoursesFragment
import com.example.educationsupport.fragment.learner.HomeFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class EducatorMainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educator_main)

        /**
         * Set up the toolbar
         */
        toolbar= findViewById(R.id.educatorHomeToolbar)
        setSupportActionBar(toolbar)

        /**
         * Display application icon in the toolbar
         */
        supportActionBar!!.title = ""

        /**
         * Replacing App Bar
         */
        appBarLayout = findViewById(R.id.educatorAppBar)

        /**
         * Set up the navigation view
         */
        bottomNavigationView = findViewById(R.id.educator_bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.educator_nav_home -> selectedFragment(EducatorHomeFragment())
                R.id.educator_add_course -> selectedFragment(AddNewCourseFragment())
                R.id.educator_add_leaner -> selectedFragment(AddNewLearnerFragment())
            }
            return@setOnItemSelectedListener false
        }
        bottomNavigationView.selectedItemId = R.id.educator_nav_home
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