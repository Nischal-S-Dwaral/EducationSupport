package com.example.educationsupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.educationsupport.fragment.CourseListFragment
import com.example.educationsupport.fragment.EnrolledCoursesFragment
import com.example.educationsupport.fragment.HomeFragment
import com.example.educationsupport.fragment.MyActivitiesFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
         * Set up the navigation view
         */
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> selectedFragment(HomeFragment())
                R.id.nav_course_list -> selectedFragment(CourseListFragment())
                R.id.nav_enrolled_courses -> selectedFragment(EnrolledCoursesFragment())
                R.id.nav_my_activities -> selectedFragment(MyActivitiesFragment())
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